package com.example.mammaldex.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mammaldex.model.database.DatabaseInitializer;
import com.example.mammaldex.MammalDexApp;
import com.example.mammaldex.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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

        //Get the navigation bar by id on activity view
        BottomNavigationView navigationBar = findViewById(R.id.navigationBar);

        //Set the home fragment as the default page.
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new HomeFragment()).commit();
        }

        //Adds a select listener to the each item in the navigation par.
        navigationBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            /**
             * Whenever a button on the nav bar is clicked, it will switch the fragment page
             * to the corresponding button
             * @param menuItem The button thats being selected
             * @return true for having the button highlights but also switches the fragment page
             */
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                //Current selected Fragment
                Fragment selectedFragment = null;

                if(menuItem.getItemId() == R.id.nav_search){
                    selectedFragment = new SearchFragment();
                }
                else
                    //If an invalid button is pressed, go to default home fragment
                    selectedFragment = new HomeFragment();

                //Switch the fragment page
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, selectedFragment).commit();

                //Highlight the button that was selected.
                return true;
            }
        });


    }
}