package com.example.mammaldex.model.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mammaldex.model.database.entities.Species;

import java.util.List;

/**
 * This Data Access Object contains various SQLite statements for retrieving,
 * updating, and deleting data relating to the species of the available mammals
 * in the MammalDex database. It uses Room to enable direct SQLite calling
 * from method prototypes.
 */
@Dao
public interface SpeciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<Species> species);

    @Query("SELECT * FROM species")
    LiveData<List<Species>> getAll();

    /**
     * This method prototype selects all species that include the user's inputted String
     * in the search bar, such that it may be an exact match or a substring for available
     * mammals.
     * @param search the user's String input in the search bar
     * @return a LiveData container that notifies the associated observer when the list of
     *         Species objects first loads or changes.
     */
    @Query("SELECT * FROM species " +
            "LEFT JOIN alternate_species_name ON species.id = alternate_species_name.species_id " +
            "WHERE species.name LIKE '%' || :search || '%' " +
            "OR species.sci_name LIKE '%' || :search || '%' " +
            "OR alternate_species_name.name LIKE '%' || :search || '%'")
    LiveData<List<Species>> getSpeciesWhereNamesContains(String search);

    /**
     * This method prototype selects the mammal by its scientific (Latin) name.
     * @param sciName the mammal's scientific name in the local database
     * @return a Species object that corresponds to the scientific name
     */
    @Query("SELECT * FROM species WHERE sci_name = :sciName")
    Species getBySciName(String sciName);

    /**
     * This method prototype selects the mammal by its scientific (Latin) name.
     * @param id the mammal's identification number in the local database
     * @return a Species object that corresponds to the identification number
     */
    @Query("SELECT * FROM species WHERE id = :id")
    Species getById(int id);

    /**
     * This method prototype deletes all rows in the "species" table.
     */
    @Query("DELETE FROM species")
    void deleteAll();

    /**
     * This method prototype updates a mammal's image path in the local database.
     * @param sciName the scientific (Latin) name of the mammal
     * @param newImagePath the path for the mammal's new image
     */
    @Query("UPDATE species SET image_path = :newImagePath WHERE sci_name = :sciName")
    void updateImagePath(String sciName, String newImagePath);

    /**
     * This method prototype updates a mammal's Wikipedia link in the local database.
     * @param sciName the scientific (Latin) name of the mammal
     * @param newWikiLink the mammal's new Wikipedia link
     */
    @Query("UPDATE species SET wiki_link = :newWikiLink WHERE sci_name = :sciName")
    void updateWikiLink(String sciName, String newWikiLink);

    /**
     * This method prototype counts the number of mammals there are in the species
     * table in the local database
     * @return the number of species as an integer
     */
    @Query("SELECT COUNT(*) FROM species")
    int size();
}

