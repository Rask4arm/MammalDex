package com.example.mammaldex.model.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mammaldex.model.database.entities.TaxonomicOrder;

import java.util.List;

@Dao
public interface TaxonomicOrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<TaxonomicOrder> orders);

    @Query("SELECT * FROM taxonomic_order")
    List<TaxonomicOrder> getAll();

    @Query("DELETE FROM taxonomic_order")
    void deleteAll();
}

