package com.example.mammaldex.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mammaldex.model.database.Repository;
import com.example.mammaldex.model.database.entities.Species;

import java.util.List;

public class SpeciesViewModel extends ViewModel {
    private final Repository repository;
    private final LiveData<List<Species>> species;

    public SpeciesViewModel(Repository repository){
        this.repository = repository;
        this.species = repository.getAllSpecies();
    }

    public LiveData<List<Species>> getSpecies() {
        return species;
    }
}
