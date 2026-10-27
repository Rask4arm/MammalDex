package com.example.mammaldex.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mammaldex.model.database.DatabaseInitializer;
import com.example.mammaldex.MammalDexApp;
import com.example.mammaldex.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        MammalDexApp app = (MammalDexApp) getApplication();

        DatabaseInitializer initializer = new DatabaseInitializer(this, app.getDatabase());

        initializer.initialize(() -> {System.out.println(app.getRepository().getAllSpecies().getValue());},
                error -> {System.out.println("Initializer didn't work");}
        );
    }
}
/**
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
                Species speciestest =
                        db.speciesDao().getById(1000001);

                runOnUiThread(() -> {
                    if (speciestest != null) {
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();

                runOnUiThread(() -> {
                    System.out.println("Something went wrong");
                });
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_host), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}*/