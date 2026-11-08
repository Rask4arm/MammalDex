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

    public LiveData<String> getSpeciesAltName(long speciesID, String searchText) {
        return repository.getSpeciesAltName(speciesID, searchText);
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

    /**
     * This method gets LiveData sorted list of species with common, scientific, and alternative names
     * that include the passed string pattern. The species are alphabetically sorted within name
     * categories, and are displayed in the order: common, scientific, alternative.
     * @param pattern the substring being searched for
     * @return a LiveData object with a sorted list of Species that have names that match the pattern
     */
    public LiveData<List<Species>> getSortedSpeciesNamesWith(String pattern) {
        return repository.getSortedSpeciesNamesWith(pattern);
    }

    /**
     * This method gets a LiveData list of species with common, scientific, and alternative names
     * that include the passed string pattern.
     * @param pattern the substring being searched for
     * @return a LiveData object with a list of Species that have names that match the pattern
     */
    public LiveData<Species> getSpeciesBySciName(String pattern) {
        return repository.getSpeciesBySciName(pattern);
    }

}
