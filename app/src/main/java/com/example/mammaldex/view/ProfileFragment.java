package com.example.mammaldex.view;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.mammaldex.R;

import com.google.android.material.button.MaterialButton;

/**
 * This class handles the logic needed for the Profile page.
 */
public class ProfileFragment extends Fragment {

    private MaterialButton ackBtn;
    private MaterialButton aboutUsBtn;
    private MaterialButton logoutBtn;
    private MaterialButton resyncBtn;
    private TextView dateSynced;

    public ProfileFragment(){

        super(R.layout.fragment_profile);

//        logoutBtn = getView().findViewById(R.id.logoutBtn);
//        logoutBtn.setOnClickListener(v -> logout());
    }

    /**
     * TODO: create a logout function that terminates the firebase connection and returns to the login page
     */
    private void logout(){


    }

}
