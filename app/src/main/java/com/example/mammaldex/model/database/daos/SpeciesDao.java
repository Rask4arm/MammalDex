package com.example.mammaldex.model.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mammaldex.model.database.entities.Species;

import java.util.List;

@Dao
public interface SpeciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<Species> species);

    @Query("SELECT * FROM species")
    List<Species> getAll();

    @Query("SELECT * FROM species WHERE sci_name = :sciName")
    Species getBySciName(String sciName);

    @Query("SELECT * FROM species WHERE id = :id")
    Species getById(int id);

    @Query("DELETE FROM species")
    void deleteAll();

    @Query("UPDATE species SET image_path = :newImagePath WHERE sci_name = :sciName")
    void updateImagePath(String sciName, String newImagePath);

    @Query("UPDATE species SET wiki_link = :newWikiLink WHERE sci_name = :sciName")
    void updateWikiLink(String sciName, String newWikiLink);

    @Query("SELECT COUNT(*) FROM species")
    int size();
}

