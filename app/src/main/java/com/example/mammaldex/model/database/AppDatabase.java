package com.example.mammaldex.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mammaldex.model.database.daos.AlternateSpeciesNameDao;
import com.example.mammaldex.model.database.daos.SightingDao;
import com.example.mammaldex.model.database.daos.SpeciesDao;
import com.example.mammaldex.model.database.entities.AlternateSpeciesName;
import com.example.mammaldex.model.database.entities.Sighting;
import com.example.mammaldex.model.database.entities.Species;

@Database(entities = {Species.class, AlternateSpeciesName.class, Sighting.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SpeciesDao speciesDao();
    public abstract AlternateSpeciesNameDao alternateSpeciesNameDao();
    public abstract SightingDao sightingDao();
}
