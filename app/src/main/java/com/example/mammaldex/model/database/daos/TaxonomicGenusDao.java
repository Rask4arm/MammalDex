package com.example.mammaldex.model.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mammaldex.model.database.entities.TaxonomicGenus;

import java.util.List;

@Dao
public interface TaxonomicGenusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TaxonomicGenus> user);

    @Query("SELECT * FROM taxonomic_genus")
    List<TaxonomicGenus> getAll();
}

