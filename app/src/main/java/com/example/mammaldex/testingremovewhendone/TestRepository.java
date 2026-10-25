package com.example.mammaldex.testingremovewhendone;

import com.example.mammaldex.model.database.entities.Species;

import java.util.ArrayList;
import java.util.List;

public class TestRepository {

    public String getMessage() {
        return "Hello from MVVM!";
    }

    public List<Species> getSpecies() {
        List<Species> species = new ArrayList<>();

        species.add(new Species(1000001L, "Platypus", "Ornithorhynchus_anatinus", "Ornithorhynchus", "Ornithorhynchidae", "Monotremata","ornithorhynchus_anatinus", "https://en.wikipedia.org/wiki/Ornithorhynchus_anatinus"));
        species.add(new Species(1000002L, "Platypus2", "Ornithorhynchus_anatinus", "Ornithorhynchus", "Ornithorhynchidae", "Monotremata","ornithorhynchus_anatinus", "https://en.wikipedia.org/wiki/Ornithorhynchus_anatinus"));
        species.add(new Species(1000003L, "Platypus3", "Ornithorhynchus_anatinus", "Ornithorhynchus", "Ornithorhynchidae", "Monotremata","ornithorhynchus_anatinus", "https://en.wikipedia.org/wiki/Ornithorhynchus_anatinus"));
        species.add(new Species(1000004L, "Platypus4", "Ornithorhynchus_anatinus", "Ornithorhynchus", "Ornithorhynchidae", "Monotremata","ornithorhynchus_anatinus", "https://en.wikipedia.org/wiki/Ornithorhynchus_anatinus"));
        species.add(new Species(1000005L, "Platypus5", "Ornithorhynchus_anatinus", "Ornithorhynchus", "Ornithorhynchidae", "Monotremata","ornithorhynchus_anatinus", "https://en.wikipedia.org/wiki/Ornithorhynchus_anatinus"));

        return species;
    }
}
