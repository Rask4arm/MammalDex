package com.example.mammaldex.model.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mammaldex.model.database.entities.TaxonomicFamily;

import java.util.List;

@Dao
public interface TaxonomicFamilyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TaxonomicFamily> user);

    @Query("SELECT * FROM taxonomic_family")
    List<TaxonomicFamily> getAll();
}

