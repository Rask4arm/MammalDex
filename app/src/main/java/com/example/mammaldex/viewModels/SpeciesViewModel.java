package com.example.mammaldex.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mammaldex.model.Repository;
import com.example.mammaldex.model.database.entities.Species;

import java.util.List;

public class SpeciesViewModel extends ViewModel {
    private final Repository repository;
    private final LiveData<List<Species>> species;
    private final MutableLiveData<String> filter = new MutableLiveData<>("");
    private final MutableLiveData<String> search = new MutableLiveData<>("");

    /**
     * This constructor sets the repository and creates an observer
     * that checks for changes in the search. If it finds a search it
     * gets the species in the MammalDex database that contain
     * the passed string pattern. It checks the available scientific (Latin),
     * common, and other names to ensure a complete search.
     * @param repository The repository for the app
     */
    public SpeciesViewModel(Repository repository){
        this.repository = repository;
        //observer that triggers when the search text changes
        this.species = Transformations.switchMap(
                search,
                s -> {
                    if (s == null || s.trim().isEmpty()) {
                        return repository.getSortedSpeciesNamesWith("");
                    } else {
                        return repository.getSortedSpeciesNamesWith(s);
                    }
                }
        );
    }

    /**
     * This method gets all species of a given family from the local database through
     * the application's data repository.
     * @param family a String representing the target family
     * @return a LiveData object with a list of species of the given family.
     */
    public LiveData<List<Species>> getAllSpeciesByFamily(String family) {
        return repository.getAllSpeciesByFamily(family);
    }

    /**
     * This method gets all species of a given genus from the local database through
     * the application's data repository.
     * @param genus a String representing the target genus
     * @return a LiveData object with a list of species of the given genus.
     */
    public LiveData<List<Species>> getAllSpeciesByGenus(String genus) {
        return repository.getAllSpeciesByGenus(genus);
    }

    /**
     * This method gets all species of a given order from the local database through
     * the application's data repository.
     * @param order a String representing the target order
     * @return a LiveData object with a list of species of the given order.
     */
    public LiveData<List<Species>> getAllSpeciesByOrder(String order) {
        return repository.getAllSpeciesByOrder(order);
    }

    /**
     * This method gets all unique families for the species from the local database
     * through the application's data repository.
     * @return a LiveData object with a list of family names as strings.
     */
    public LiveData<List<String>> getDistinctFamilies() {
        return repository.getDistinctFamilies();
    }

    /**
     * This method gets all unique genera for the species from the local database
     * through the application's data repository.
     * @return a LiveData object with a list of genera names as strings.
     */
    public LiveData<List<String>> getDistinctGenera() {
        return repository.getDistinctGenera();
    }

    /**
     * This method gets all unique orders for the species from the local database
     * through the application's data repository.
     * @return a LiveData object with a list of orders names as strings.
     */
    public LiveData<List<String>> getDistinctOrders() {
        return repository.getDistinctOrders();
    }

    /**
     * This method gets the species that should be displayed from
     * the MammalDex database.
     * @return a LiveData list of all the species in the database
     */
    public LiveData<List<Species>> getDisplayedSpecies() {
        return species;
    }

    /**
     * This method gets all Alternate species names in the database by ID
     * @param speciesID the long variable to use to search for all names with the ID
     * @return A LiveData string list of alternate names of the species.
     */
    public LiveData<List<String>> getSpeciesAltName(long speciesID) {
        return repository.getSpeciesAltName(speciesID);
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

    /**
     * Changes the string in the search mutable data so
     * @param searchText the text that you want to search for
     */
    public void setSearchText(String searchText) {
        search.setValue(searchText);
    }

    public void setSpeciesAsSeen(String sci_name) {
        repository.setSeenTrue(sci_name);
    }

    public void setSpeciesAsNotSeen(String sci_name) {
        repository.setSeenFalse(sci_name);
    }

}
