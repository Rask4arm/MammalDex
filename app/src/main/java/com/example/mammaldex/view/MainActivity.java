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

        initializer.initialize(() -> {},
                error -> {System.out.println("Initializer didn't work");}
        );
    }
}