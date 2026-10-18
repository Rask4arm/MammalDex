package com.example.mammaldex;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;
import androidx.sqlite.driver.AndroidSQLiteDriver;

import com.example.mammaldex.model.database.AppDatabase;
import com.example.mammaldex.model.database.DatabaseCreator;
import com.example.mammaldex.model.database.ImagePathMatcher;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

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


        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                db.speciesDao().deleteAll();
                db.taxonomicGenusDao().deleteAll();
                db.taxonomicFamilyDao().deleteAll();
                db.taxonomicOrderDao().deleteAll();
                DatabaseCreator.readCsvToRoom(getApplicationContext(), db);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setImage();
    }

    public void setImage(){
        ImageView imageView = findViewById(R.id.platypus);
        imageView.setImageResource(R.drawable.ornithorhynchus_anatinus);
    }
}