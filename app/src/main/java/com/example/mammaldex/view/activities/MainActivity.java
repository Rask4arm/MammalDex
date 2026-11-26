package com.example.mammaldex.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mammaldex.databinding.ActivityMainBinding;
import com.example.mammaldex.model.database.DatabaseInitializer;
import com.example.mammaldex.MammalDexApp;
import com.example.mammaldex.R;
import com.example.mammaldex.view.fragments.HomeFragment;
import com.example.mammaldex.view.fragments.MapFragment;
import com.example.mammaldex.view.fragments.ProfileFragment;
import com.example.mammaldex.view.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    /**
     * TODO Add userid field that gets saved on login, and null on logout
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initializes view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        MammalDexApp app = (MammalDexApp) getApplication();
        DatabaseInitializer initializer = new DatabaseInitializer(this, app.getDatabase());

        initializer.initialize(() -> {
            Log.d("INFO", "Initializer worked");},
                error -> {Log.d("INFO", "Initializer didn't work");}
        );

        //Get the navigation bar by id on activity view
        BottomNavigationView navigationBar = findViewById(R.id.navigationBar);

        //Set the home fragment as the default page.
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new HomeFragment()).commit();
        }

        //Adds a select listener to each item in the navigation par.
        navigationBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            /**
             * Whenever a button on the nav bar is clicked, it will switch the fragment page
             * to the corresponding button
             * @param menuItem The button that's being selected
             * @return true for having the button highlights but also switches the fragment page
             */
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                //Current selected Fragment
                Fragment selectedFragment = null;

                //Get the menuItem's Id
                int menuId = menuItem.getItemId();

                //Selected the new fragment depending on ID.
                if(menuId == R.id.nav_search){
                    selectedFragment = new SearchFragment();
                }
                else if(menuId == R.id.nav_map){
                    selectedFragment = new MapFragment();
                }
                else if(menuId == R.id.nav_profile){
                    selectedFragment = new ProfileFragment();
                }
                else
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