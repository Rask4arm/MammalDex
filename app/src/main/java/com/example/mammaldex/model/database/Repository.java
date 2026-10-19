package com.example.mammaldex.model.database;

import androidx.lifecycle.LiveData;

import com.example.mammaldex.model.database.daos.SpeciesDao;
import com.example.mammaldex.model.database.entities.Species;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class Repository {
    private final SpeciesDao speciesDao;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public Repository(SpeciesDao speciesDao){
        this.speciesDao = speciesDao;
    }

    public void getAllSpeciesByAsync(Consumer<List<Species>> callback) {
        executorService.submit(() -> {

            System.out.println(speciesDao.size() + "size");
            // 1. Fetch the species on the background thread
            List<Species> speciesList = speciesDao.getAll();

            // 2. Deliver the result asynchronously
            callback.accept(speciesList);
        });
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
