package com.example.mammaldex.model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.example.mammaldex.model.database.daos.SpeciesDao;
import com.example.mammaldex.model.database.entities.Species;
import com.example.mammaldex.model.database.local.AppDatabase;
import com.example.mammaldex.model.database.sync.SyncScheduler;

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
    private final Context context;
    public Repository(AppDatabase db, Context context){
        this.speciesDao = db.speciesDao();
        this.context = context.getApplicationContext();
    }

    //Getters and setters
    public LiveData<List<Species>> getAllSpecies() {
        return speciesDao.getAll();
    }

    public LiveData<List<Species>> getAllSpeciesByFamily(String family) {
        return speciesDao.getAllSpeciesByFamily(family);
    }

    public LiveData<List<Species>> getAllSpeciesByGenus(String genus) {
        return speciesDao.getAllSpeciesByGenus(genus);
    }

    public LiveData<List<Species>> getAllSpeciesByOrder(String order) {
        return speciesDao.getAllSpeciesByOrder(order);
    }

    public LiveData<List<String>> getDistinctFamilies(String order) {
        return speciesDao.getDistinctFamiliesByOrder(order);
    }

    public LiveData<List<String>> getDistinctGenera(String family) {
        return speciesDao.getDistinctGeneraByFamily(family);
    }

    public LiveData<List<String>> getDistinctOrders() {
        return speciesDao.getDistinctOrders();
    }

    public LiveData<String> getSpeciesAltName(long speciesID, String searchText) {
        return speciesDao.getSpeciesAltName(speciesID, searchText);
    }

    public LiveData<List<String>> getSpeciesAltName(long speciesID) {
        return speciesDao.getSpeciesAltName(speciesID);
    }
    

    public LiveData<List<Species>> getSpeciesNamesWith(String pattern) {
        return speciesDao.getSpeciesNamesWith(pattern);
    }

    public LiveData<List<Species>> getSortedSpeciesNamesWith(String pattern) {
        return speciesDao.getSortedSpeciesNamesWith(pattern);
    }

    public LiveData<List<Species>> getSortedSpeciesNamesByFamilyWhere(String pattern, String family) {
        return speciesDao.getSortedSpeciesNamesByFamilyWhere(pattern, family);
    }

    public LiveData<List<Species>> getSortedSpeciesNamesByGenusWhere(String pattern, String genus) {
        return speciesDao.getSortedSpeciesNamesByGenusWhere(pattern, genus);
    }

    public LiveData<List<Species>> getSortedSpeciesNamesByOrderWhere(String pattern, String order) {
        return speciesDao.getSortedSpeciesNamesByOrderWhere(pattern, order);
    }
    
    public LiveData<Species> getSpeciesBySciName(String name) {
        return speciesDao.getBySciName(name);
    }

    public Species getSpeciesById(int id){
        return speciesDao.getById(id);
    }

    public void setSeenTrue(String name) {
        executorService.execute(() -> speciesDao.updateSeen(name, 1));
        SyncScheduler.scheduleSync(context);
    }

    public void setSeenFalse(String name) {
        executorService.execute(() -> speciesDao.updateSeen(name, 0));
        SyncScheduler.scheduleSync(context);
    }

}
