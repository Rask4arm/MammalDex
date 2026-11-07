package com.example.mammaldex.model;

import androidx.lifecycle.LiveData;

import com.example.mammaldex.model.database.daos.SpeciesDao;
import com.example.mammaldex.model.database.entities.Species;
import com.example.mammaldex.model.database.local.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final SpeciesDao speciesDao;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    public Repository(AppDatabase db){
        this.speciesDao = db.speciesDao();
    }

    public LiveData<List<Species>> getAllSpecies() {
        return speciesDao.getAll();
    }

    public LiveData<List<Species>> getSpeciesNamesWith(String search) {
        return speciesDao.getSpeciesNamesWith(search);
    }

    public void setSeenTrue(String name) {
        speciesDao.updateSeen(name, 1);
    }

    public void setSeenFalse(String name) {
        speciesDao.updateSeen(name, 0);
    }

}
