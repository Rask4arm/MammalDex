package com.example.mammaldex.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mammaldex.model.Repository;
import com.example.mammaldex.model.database.entities.Species;

import java.util.List;

public class SpeciesViewModel extends ViewModel {
    private final Repository repository;
    private final LiveData<List<Species>> species;

    public SpeciesViewModel(Repository repository){
        this.repository = repository;
        this.species = repository.getAllSpecies();
    }

    /**
     * This method gets all species in the MammalDex database.
     * @return a LiveData list of all the species in the database
     */
    public LiveData<List<Species>> getAllSpecies() {
        return species;
    }

    /**
     * This method gets the species in the MammalDex database that contain
     * the passed string pattern. It checks the available scientific (Latin),
     * common, and other names to ensure a complete search.
     * @param pattern the substring to search for in the database's species names.
     * @return a LiveData list of all species with names that contain the pattern
     */
    public LiveData<List<Species>> getSpeciesNamesWith(String pattern) {
        return repository.getSpeciesNamesWith(pattern);
    }

}
