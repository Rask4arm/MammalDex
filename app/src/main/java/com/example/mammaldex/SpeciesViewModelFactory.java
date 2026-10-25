package com.example.mammaldex;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mammaldex.model.database.Repository;
import com.example.mammaldex.viewModels.SpeciesViewModel;

public class SpeciesViewModelFactory implements ViewModelProvider.Factory {
    private final Repository repository;

    public SpeciesViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SpeciesViewModel.class)) {
            return modelClass.cast(new SpeciesViewModel(repository));
        }
        throw new IllegalArgumentException("ViewModel class is wrong");
    }
}
