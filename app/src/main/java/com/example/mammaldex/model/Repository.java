package com.example.mammaldex.model;

import androidx.lifecycle.LiveData;

import com.example.mammaldex.model.database.daos.SpeciesDao;
import com.example.mammaldex.model.database.entities.Species;
import com.example.mammaldex.model.database.local.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Repository {
    private final SpeciesDao speciesDao;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    public Repository(AppDatabase db){
        this.speciesDao = db.speciesDao();
    }

    public LiveData<List<Species>> getAllSpecies() {
        return speciesDao.getAll();
    }

    public void getSpeciesBySciNameAsync(String sciName, Consumer<Species> callback) {
        executorService.submit(() -> {

            System.out.println(speciesDao.size() + "size");
            // 1. Fetch the species on the background thread
            Species species = speciesDao.getBySciName(sciName);

            // 2. Deliver the result asynchronously
            callback.accept(species);
        });
    }
}
