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

    /**
     * This method gets all species in the local database from the species table.
     * @return a LiveData object with a list of all species.
     */
    @Query("SELECT * FROM species")
    LiveData<List<Species>> getAll();

    /**
     * This method gets all species in the local database from the species table
     * with the given string as their "family" attribute value.
     * @param family a String representing the target family
     * @return a LiveData object with a list of all species in the given family.
     */
    @Query("SELECT * FROM species WHERE taxonomic_family = :family")
    LiveData<List<Species>> getAllSpeciesByFamily(String family);

    /**
     * This method gets all species in the local database from the species table
     * with the given string as their "genus" attribute value.
     * @param genus a String representing the target genus
     * @return a LiveData object with a list of all species in the given genus.
     */
    @Query("SELECT * FROM species WHERE taxonomic_genus = :genus")
    LiveData<List<Species>> getAllSpeciesByGenus(String genus);

    /**
     * This method gets all species in the local database from the species table
     * with the given string as their "order" attribute value.
     * @param order a String representing the target order
     * @return a LiveData object with a list of all species in the given order.
     */
    @Query("SELECT * FROM species WHERE taxonomic_order = :order")
    LiveData<List<Species>> getAllSpeciesByOrder(String order);

    /**
     * This method gets all the unique family names in the local database from the
     * species table with the given order.
     * @param order the target order for families to belong to.
     * @return a LiveData object with a list of all unique family names as strings
     * with the specified order.
     */
    @Query("SELECT DISTINCT taxonomic_family FROM species WHERE taxonomic_order = :order")
    LiveData<List<String>> getDistinctFamiliesByOrder(String order);

    /**
     * This method gets all the unique genera names in the local database from the
     * species table with the given family.
     * @param family the target order for families to belong to.
     * @return a LiveData object with a list of all unique family names as strings
     * with the specified family.
     */
    @Query("SELECT DISTINCT taxonomic_genus FROM species WHERE taxonomic_family = :family")
    LiveData<List<String>> getDistinctGeneraByFamily(String family);

    /**
     * This method gets all the unique order names in the local database from the
     * species table.
     * @return a LiveData object with a list of all unique order names as strings.
     */
    @Query("SELECT DISTINCT taxonomic_order FROM species")
    LiveData<List<String>> getDistinctOrders();

    /**
     * This method gets the alternative names for a specific species that matches
     * the passed ID.
     * @param speciesID the unique identification number that corresponds to the
     *                  targeted species
     * @return a String representing a delimited set of alternative names for the
     *         targeted species.
     */
    @Query("SELECT name FROM alternate_species_name " +
            "WHERE species_id = :speciesID " +
            "   AND name LIKE '%' || :searchText || '%' ")
    LiveData<String> getSpeciesAltName(long speciesID, String searchText);

    /**
     * This method gets the alternative names for a specific species that matches
     * the passed ID.
     * @param speciesID the unique identification number that corresponds to the
     *                  targeted species
     * @return a String representing a delimited set of alternative names for the
     *         targeted species.
     */
    @Query("SELECT name FROM alternate_species_name " +
            "WHERE species_id = :speciesID ")
    LiveData<List<String>> getSpeciesAltName(long speciesID);

    /**
     * This method prototype selects all species that include the user's inputted String
     * in the search bar, such that it may be an exact match or a substring for available
     * mammals.
     * @param pattern the user's String input in the search bar
     * @return a LiveData container with the results of the search
     */
    @Query("SELECT DISTINCT species.* FROM species " +
            "LEFT JOIN alternate_species_name ON species.id = alternate_species_name.species_id " +
            "WHERE species.name LIKE '%' || :pattern || '%' " +
            "OR species.sci_name LIKE '%' || :pattern || '%' " +
            "OR alternate_species_name.name LIKE '%' || :pattern || '%'")
    LiveData<List<Species>> getSpeciesNamesWith(String pattern);

    /**
     * This method prototype selects all species that include the user's inputted String
     * in the search bar, such that it may be an exact match or a substring for available
     * mammals. The rows are sorted alphabetically within name categories (tiers 0, 1, 2)
     * and ordered by: name, sci_name, alternate_species_names.
     * @param pattern the user's String input in the search bar
     * @return a LiveData container with the results of the search
     */
    @Query("SELECT DISTINCT species.* FROM species " +
            "LEFT JOIN alternate_species_name ON species.id = alternate_species_name.species_id " +
            "WHERE species.name LIKE '%' || :pattern || '%' " +
            "OR species.sci_name LIKE '%' || :pattern || '%' " +
            "OR alternate_species_name.name LIKE '%' || :pattern || '%' " +
            "ORDER BY " +
            "   CASE " +
            "       WHEN species.name LIKE '%' || :pattern || '%' THEN 0 " +
            "       WHEN species.sci_name LIKE '%' || :pattern || '%' THEN 1 " +
            "       ELSE 2 " +
            "   END, " +
            "   CASE " +
            "       WHEN species.name LIKE '%' || :pattern || '%' THEN species.name " +
            "       WHEN species.sci_name LIKE '%' || :pattern || '%' THEN species.sci_name " +
            "   END COLLATE NOCASE")
    LiveData<List<Species>> getSortedSpeciesNamesWith(String pattern);

    /**
     * This method prototype filters all species that include the user's inputted String
     * in the search bar, such that it may be an exact match or a substring for available
     * mammals. The rows are sorted alphabetically within name categories (tiers 0, 1, 2)
     * and ordered by name, sci_name, alternate_species_names, with all results having
     * the specified family.
     * @param pattern the user's String input in the search bar
     * @param family the selected family as a search filter
     * @return a LiveData container with the results of the search
     */
    @Query("SELECT DISTINCT species.* FROM species " +
            "LEFT JOIN alternate_species_name ON species.id = alternate_species_name.species_id " +
            "WHERE (species.name LIKE '%' || :pattern || '%' " +
            "OR species.sci_name LIKE '%' || :pattern || '%' " +
            "OR alternate_species_name.name LIKE '%' || :pattern || '%')" +
            "AND species.taxonomic_order = :family " +
            "ORDER BY " +
            "   CASE " +
            "       WHEN species.name LIKE '%' || :pattern || '%' THEN 0 " +
            "       WHEN species.sci_name LIKE '%' || :pattern || '%' THEN 1 " +
            "       ELSE 2 " +
            "   END, " +
            "   CASE " +
            "       WHEN species.name LIKE '%' || :pattern || '%' THEN species.name " +
            "       WHEN species.sci_name LIKE '%' || :pattern || '%' THEN species.sci_name " +
            "   END COLLATE NOCASE")
    LiveData<List<Species>> getSortedSpeciesNamesByFamilyWhere(String pattern, String family);

    /**
     * This method prototype filters all species that include the user's inputted String
     * in the search bar, such that it may be an exact match or a substring for available
     * mammals. The rows are sorted alphabetically within name categories (tiers 0, 1, 2)
     * and ordered by name, sci_name, alternate_species_names, with all results having
     * the specified genus.
     * @param pattern the user's String input in the search bar
     * @param genus the selected genus as a search filter
     * @return a LiveData container with the results of the search
     */
    @Query("SELECT DISTINCT species.* FROM species " +
            "LEFT JOIN alternate_species_name ON species.id = alternate_species_name.species_id " +
            "WHERE (species.name LIKE '%' || :pattern || '%' " +
            "OR species.sci_name LIKE '%' || :pattern || '%' " +
            "OR alternate_species_name.name LIKE '%' || :pattern || '%')" +
            "AND species.taxonomic_order = :genus " +
            "ORDER BY " +
            "   CASE " +
            "       WHEN species.name LIKE '%' || :pattern || '%' THEN 0 " +
            "       WHEN species.sci_name LIKE '%' || :pattern || '%' THEN 1 " +
            "       ELSE 2 " +
            "   END, " +
            "   CASE " +
            "       WHEN species.name LIKE '%' || :pattern || '%' THEN species.name " +
            "       WHEN species.sci_name LIKE '%' || :pattern || '%' THEN species.sci_name " +
            "   END COLLATE NOCASE")
    LiveData<List<Species>> getSortedSpeciesNamesByGenusWhere(String pattern, String genus);

    /**
     * This method prototype filters all species that include the user's inputted String
     * in the search bar, such that it may be an exact match or a substring for available
     * mammals. The rows are sorted alphabetically within name categories (tiers 0, 1, 2)
     * and ordered by name, sci_name, alternate_species_names, with all results having
     * the specified order.
     * @param pattern the user's String input in the search bar
     * @param orderName the selected order as a search filter
     * @return a LiveData container with the results of the search
     */
    @Query("SELECT DISTINCT species.* FROM species " +
            "LEFT JOIN alternate_species_name ON species.id = alternate_species_name.species_id " +
            "WHERE (species.name LIKE '%' || :pattern || '%' " +
            "OR species.sci_name LIKE '%' || :pattern || '%' " +
            "OR alternate_species_name.name LIKE '%' || :pattern || '%')" +
            "AND species.taxonomic_order = :orderName " +
            "ORDER BY " +
            "   CASE " +
            "       WHEN species.name LIKE '%' || :pattern || '%' THEN 0 " +
            "       WHEN species.sci_name LIKE '%' || :pattern || '%' THEN 1 " +
            "       ELSE 2 " +
            "   END, " +
            "   CASE " +
            "       WHEN species.name LIKE '%' || :pattern || '%' THEN species.name " +
            "       WHEN species.sci_name LIKE '%' || :pattern || '%' THEN species.sci_name " +
            "   END COLLATE NOCASE")
    LiveData<List<Species>> getSortedSpeciesNamesByOrderWhere(String pattern, String orderName);

    /**
     * This method prototype selects the mammal by its scientific (Latin) name.
     * @param sciName the mammal's scientific name in the local database
     * @return a Species object that corresponds to the scientific name
     */
    @Query("SELECT * FROM species WHERE sci_name = :sciName")
    LiveData<Species> getBySciName(String sciName);

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

    @Query("UPDATE species SET seen = :seenInt WHERE sci_name = :name")
    void updateSeen(String name, int seenInt);
}

