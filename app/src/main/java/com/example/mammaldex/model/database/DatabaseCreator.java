package com.example.mammaldex.model.database;

import android.content.Context;

import com.example.mammaldex.model.database.entities.Species;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DatabaseCreator {
    public static void readCsvToRoom(Context context, AppDatabase database) {
        List<Species> speciesList = new ArrayList<>();

        try {
            InputStream is = context.getAssets().open("data.csv");
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

                    String wikiLink = "https://en.wikipedia.org/wiki/" + sciName;
                    String imagePath = sciName.toLowerCase();

                    speciesList.add(new Species(Long.parseLong(id), name, sciName, genus, family, order, imagePath, wikiLink));

                }
            }
            reader.close();

            List<Long> speciesIds = new ArrayList<>();

            // Insert data into Room if the list isn't empty
            if (!speciesList.isEmpty()) {
                speciesIds = database.speciesDao().insertAll(speciesList);
                System.out.println("Species done inserting");
            }
            System.out.println("database generated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
