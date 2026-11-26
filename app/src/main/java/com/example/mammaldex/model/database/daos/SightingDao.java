package com.example.mammaldex.model.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mammaldex.model.database.entities.Sighting;

import java.util.List;

@Dao
public interface SightingDao {

    /**
     * Method to insert all the sightings into the database
     * @param sightings List of all the sightings to add
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Sighting> sightings);

    /**
     * Method that uses SQL to get all the sightings from the database
     * @return A list of all the sightings in the database
     */
    @Query("SELECT * FROM sighting")
    List<Sighting> getAll();
}

