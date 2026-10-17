package com.example.mammaldex.model.database;

import android.content.Context;

import com.example.mammaldex.model.database.entities.TaxonomicFamily;
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


        Map<String, String> familyToOrder = new HashMap<>();
        Map<String, String> genusToFamily = new HashMap<>();
        Map<String, String> speciesToGenus = new HashMap<>();

        try {
            InputStream is = context.getAssets().open("csv test.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            // Skip the header row
            String headerLine = reader.readLine();

            int orderCol = 18;
            int familyCol = 14;
            int genusCol = 9;
            int speciesCol = 4;


            while ((line = reader.readLine()) != null) {
                // Split by comma (handles standard CSV formats)
                String[] tokens = line.split(",");
                if (tokens.length >= 18) {
                    String sciName = tokens[1].trim();
                    String id = tokens[2].trim();
                    String name = tokens[speciesCol].trim();
                    String otherNames = tokens[5].trim();
                    String genus = tokens[genusCol].trim();
                    String family = tokens[familyCol].trim();
                    String order = tokens[orderCol].trim();

                    orderList.add(new TaxonomicOrder(order));
                    familyToOrder.put(family, order);
                    genusToFamily.put(genus, family);
                    speciesToGenus.put(name, genus);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    private static void addUnique(List<TaxonomicOrder> list, String string) {
        boolean bool = true;
        for(TaxonomicOrder taxonomicOrder : list) {
            if(Objects.equals(taxonomicOrder.getOrderName(), string)) {
                bool = false;
            }
        }
        if (bool == true) {
            list.add(new TaxonomicOrder(string));
        }
    }*/
}
