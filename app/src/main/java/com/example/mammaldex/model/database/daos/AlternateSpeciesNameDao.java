package com.example.mammaldex.model.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mammaldex.model.database.entities.AlternateSpeciesName;

import java.util.List;

@Dao
public interface AlternateSpeciesNameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AlternateSpeciesName> alternateSpeciesNames);

    @Query("SELECT * FROM alternate_species_name")
    List<AlternateSpeciesName> getAll();
}

