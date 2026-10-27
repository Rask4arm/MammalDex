package com.example.mammaldex.model.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mammaldex.model.database.entities.Sighting;

import java.util.List;

@Dao
public interface SightingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Sighting> sightings);

    @Query("SELECT * FROM sighting")
    List<Sighting> getAll();
}

