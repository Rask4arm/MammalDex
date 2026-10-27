package com.example.mammaldex;

import android.app.Application;

import com.example.mammaldex.model.database.local.AppDatabase;
import com.example.mammaldex.model.Repository;

public class MammalDexApp extends Application {
    private AppDatabase database;
    private Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();

        database = AppDatabase.getInstance(this);
        repository = new Repository(database);
    }

    public Repository getRepository() {
        return repository;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
