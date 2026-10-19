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

    public List<Species> getAllSpecies() {
        try {
            // 1. Submit the task to the single background thread
            Future<List<Species>> future = executorService.submit(() -> speciesDao.getAll());

            // 2. Block the current thread until the background task returns the list
            return future.get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return empty list or handle error appropriately
        }
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
