package com.example.mammaldex;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.example_activity);

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