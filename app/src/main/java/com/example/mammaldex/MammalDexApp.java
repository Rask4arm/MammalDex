package com.example.mammaldex;

import android.app.Application;

import com.example.mammaldex.model.database.local.AppDatabase;
import com.example.mammaldex.model.Repository;
import com.example.mammaldex.model.database.sync.SyncScheduler;

public class MammalDexApp extends Application {
    private AppDatabase database;
    private Repository repository;

    /**
     * Sets up the App
     */
    @Override
    public void onCreate() {
        super.onCreate();
        database = AppDatabase.getInstance(this);
        repository = new Repository(database, this);
        SyncScheduler.schedulePeriodicSync(this);
        SyncScheduler.scheduleSync(this);
    }

    // Getter methods
    public Repository getRepository() {
        return repository;
    }
    public AppDatabase getDatabase() {
        return database;
    }

}
