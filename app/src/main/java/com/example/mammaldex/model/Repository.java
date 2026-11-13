package com.example.mammaldex.model;

import androidx.lifecycle.LiveData;

import com.example.mammaldex.model.database.daos.SpeciesDao;
import com.example.mammaldex.model.database.entities.Species;
import com.example.mammaldex.model.database.local.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Repository is the middleman between the viewModel and the Dao.
 * Gets LiveData from the DAOs that is dynamically updated whenever the user changes the data
 */
public class Repository {
    private final SpeciesDao speciesDao;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    public Repository(AppDatabase db){
        this.speciesDao = db.speciesDao();
    }

    //Getters and setters
    public LiveData<List<Species>> getAllSpecies() {
        return speciesDao.getAll();
    }

    public LiveData<String> getSpeciesAltName(long speciesID, String searchText) {
        return speciesDao.getSpeciesAltName(speciesID, searchText);
    }

    public LiveData<List<Species>> getSpeciesNamesWith(String pattern) {
        return speciesDao.getSpeciesNamesWith(pattern);
    }

    public LiveData<List<Species>> getSortedSpeciesNamesWith(String pattern) {
        return speciesDao.getSortedSpeciesNamesWith(pattern);
    }
    
    public LiveData<Species> getSpeciesBySciName(String name) {
        return speciesDao.getBySciName(name);
    }

    public void setSeenTrue(String name) {
        speciesDao.updateSeen(name, 1);
    }

    public void setSeenFalse(String name) {
        speciesDao.updateSeen(name, 0);
    }

}
