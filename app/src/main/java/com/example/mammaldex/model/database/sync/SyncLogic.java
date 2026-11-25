package com.example.mammaldex.model.database.sync;

import com.example.mammaldex.model.database.daos.SightingDao;
import com.example.mammaldex.model.database.daos.SpeciesDao;
import com.example.mammaldex.model.database.entities.Species;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncLogic {

    SpeciesDao speciesDao;
    SightingDao sightingDao;

    public void syncData() {
        syncSpecies();
        syncSightings();
    }
    public void syncSpecies() {
        List<Species> localSpecies = speciesDao.getAll().getValue();
        Map<String, DocumentSnapshot> remoteSpecies = new HashMap<>();



        for (Species local : localSpecies) {

        }
    }

    public void syncSightings() {

    }
}
