package com.example.mammaldex.model.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mammaldex.model.database.entities.Species;

import java.util.List;

@Dao
public interface SpeciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Species> user);

    @Query("SELECT * FROM species")
    List<Species> getAll();
}

