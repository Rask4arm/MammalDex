package com.example.mammaldex.testingremovewhendone;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mammaldex.model.database.entities.Species;

import java.util.List;

public class MainViewModel extends ViewModel {

    private final TestRepository repository;
    private final MutableLiveData<String> message;
    private final MutableLiveData<List<Species>> species;

    public MainViewModel() {
        repository = new TestRepository();
        message = new MutableLiveData<>(
                repository.getMessage()
        );
        species = new MutableLiveData<>(
                repository.getSpecies()
        );
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public LiveData<List<Species>> getSpecies() {
        return species;
    }

    public void updateMessage(String newMessage) {
        message.setValue(newMessage);
    }
}
