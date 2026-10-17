package com.example.mammaldex.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mammaldex.model.database.daos.AlternateSpeciesNameDao;
import com.example.mammaldex.model.database.daos.SightingDao;
import com.example.mammaldex.model.database.daos.TaxonomicFamilyDao;
import com.example.mammaldex.model.database.daos.TaxonomicGenusDao;
import com.example.mammaldex.model.database.daos.TaxonomicOrderDao;
import com.example.mammaldex.model.database.daos.SpeciesDao;
import com.example.mammaldex.model.database.entities.AlternateSpeciesName;
import com.example.mammaldex.model.database.entities.Sighting;
import com.example.mammaldex.model.database.entities.TaxonomicFamily;
import com.example.mammaldex.model.database.entities.TaxonomicGenus;
import com.example.mammaldex.model.database.entities.TaxonomicOrder;
import com.example.mammaldex.model.database.entities.Species;

@Database(entities = {Species.class, TaxonomicGenus.class, TaxonomicFamily.class, TaxonomicOrder.class, AlternateSpeciesName.class, Sighting.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SpeciesDao speciesDao();
    public abstract AlternateSpeciesNameDao alternateSpeciesNameDao();
    public abstract SightingDao sightingDao();
    public abstract TaxonomicOrderDao taxonomicOrderDao();
    public abstract TaxonomicFamilyDao taxonomicFamilyDao();
    public abstract TaxonomicGenusDao taxonomicGenusDao();
}
