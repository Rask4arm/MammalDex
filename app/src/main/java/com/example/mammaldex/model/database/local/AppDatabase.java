package com.example.mammaldex.model.database.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.driver.AndroidSQLiteDriver;

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
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "mammal_db")
                            .setDriver(new AndroidSQLiteDriver())
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
