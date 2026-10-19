package com.example.mammaldex;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.sqlite.driver.AndroidSQLiteDriver;

import com.bumptech.glide.Glide;
import com.example.mammaldex.model.database.AppDatabase;
import com.example.mammaldex.model.database.DatabaseCreator;
import com.example.mammaldex.model.database.ImagePathMatcher;
import com.example.mammaldex.model.database.Repository;
import com.example.mammaldex.model.database.entities.Species;
import com.example.mammaldex.view.MammalViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private MammalViewAdapter mammalViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        AppDatabase db = Room.databaseBuilder(
                        getApplicationContext(),
                        AppDatabase.class,
                        "database"
                )
                .setDriver(new AndroidSQLiteDriver())
                .build();

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // Only import if the database is empty
                if (db.speciesDao().size() == 0) {
                    DatabaseCreator.readCsvToRoom(
                            getApplicationContext(), db);
                }

                // Now query for a species
                Species species =
                        db.speciesDao().getById(1000001);

                runOnUiThread(() -> {
                    if (species != null) {
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();

                runOnUiThread(() -> {
                    // Show an error if initialization or querying fails.
                });
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewMammals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Species> testList = new ArrayList<>();
        Repository repository = new Repository(db.speciesDao());
        //testList.add(new Species(1L, "10", "11", "12", "13", "14", "15", "16"));

        repository.getSpeciesBySciNameAsync("Ornithorhynchus_anatinus", species -> {
            if (species != null) {
                testList.add(species);
                System.out.println(species.getName() + "test");
            }
        });

        mammalViewAdapter = new MammalViewAdapter(testList);
        recyclerView.setAdapter(mammalViewAdapter);
    }
}