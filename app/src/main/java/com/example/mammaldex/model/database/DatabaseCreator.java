package com.example.mammaldex.model.database;

import android.content.Context;

import com.example.mammaldex.model.database.entities.Species;
import com.example.mammaldex.model.database.entities.TaxonomicFamily;
import com.example.mammaldex.model.database.entities.TaxonomicGenus;
import com.example.mammaldex.model.database.entities.TaxonomicOrder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DatabaseCreator {
    public static void readCsvToRoom(Context context, AppDatabase database) {

        List<TaxonomicOrder> orderList = new ArrayList<>();
        List<TaxonomicFamily> familyList = new ArrayList<>();
        List<TaxonomicGenus> genusList = new ArrayList<>();
        List<Species> speciesList = new ArrayList<>();

        Map<String, String> familyToOrder = new HashMap<>();
        Map<String, String> genusToFamily = new HashMap<>();
        Map<String, String> speciesToGenus = new HashMap<>();



        try {
            InputStream is = context.getAssets().open("csv test.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            // Skip the header row
            String headerLine = reader.readLine();

            while ((line = reader.readLine()) != null) {
                // Split by comma (handles standard CSV formats)
                String[] tokens = line.split(",");
                if (tokens.length >= 18) {
                    String sciName = tokens[0].trim();
                    String id = tokens[1].trim();
                    String name = tokens[3].trim();
                    String otherNames = tokens[4].trim();
                    String genus = tokens[18].trim();
                    String family = tokens[14].trim();
                    String order = tokens[9].trim();

                    orderList.add(new TaxonomicOrder(order));

                    familyToOrder.put(family, order);
                    genusToFamily.put(genus, family);
                    speciesToGenus.put(name, genus);

                    speciesList.add(new Species(Long.parseLong(id), name, sciName, 0L, "test"));

                }
            }
            reader.close();



            List<Long> orderIds = new ArrayList<>();

            // Insert data into Room if the list isn't empty
            if (!orderList.isEmpty()) {
                orderIds = database.taxonomicOrderDao().insertAll(orderList);
            }

            if (orderIds.size() != orderList.size()) {
                System.out.println("orderIds and orderList are not the same size");
            }

            Map<String, Long> orderNameToId = new HashMap<>();
            for (int i = 0; i < orderIds.size(); i++){
                orderNameToId.put(orderList.get(i).getOrderName(), orderIds.get(i));
            }

            familyToOrder.forEach((familyName, orderName) -> {
                familyList.add(new TaxonomicFamily(familyName, orderNameToId.get(orderName)));
            });



            List<Long> familyIds = new ArrayList<>();

            // Insert data into Room if the list isn't empty
            if (!familyList.isEmpty()) {
                familyIds = database.taxonomicFamilyDao().insertAll(familyList);
            }

            if (familyIds.size() != familyList.size()) {
                System.out.println("familyIds and familyList are not the same size");
            }

            Map<String, Long> familyNameToId = new HashMap<>();
            for (int i = 0; i < familyIds.size(); i++){
                familyNameToId.put(familyList.get(i).getFamilyName(), familyIds.get(i));
            }

            genusToFamily.forEach((genusName, familyName) -> {
                genusList.add(new TaxonomicGenus(genusName, orderNameToId.get(familyName)));
            });

            List<Long> genusIds = new ArrayList<>();

            // Insert data into Room if the list isn't empty
            if (!genusList.isEmpty()) {
                genusIds = database.taxonomicGenusDao().insertAll(genusList);
            }



            if (genusIds.size() != genusList.size()) {
                System.out.println("genusIds and genusList are not the same size");
            }

            Map<String, Long> genusNameToId = new HashMap<>();
            for (int i = 0; i < genusIds.size(); i++){
                genusNameToId.put(genusList.get(i).getGenusName(), genusIds.get(i));
            }

            for (int i = 0; i < speciesList.size(); i++){
                speciesList.get(i).setGenusId(
                        genusNameToId.get(
                        speciesToGenus.get(
                        speciesList.get(i).getName())));
            }

            List<Long> speciesIds = new ArrayList<>();

            // Insert data into Room if the list isn't empty
            if (!speciesList.isEmpty()) {
                speciesIds = database.speciesDao().insertAll(speciesList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
