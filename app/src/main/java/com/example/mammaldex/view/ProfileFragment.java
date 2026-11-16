package com.example.mammaldex.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mammaldex.R;

import com.example.mammaldex.databinding.FragmentProfileBinding;

/**
 * This class handles the logic needed for the Profile page.
 */
public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public ProfileFragment(){
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentProfileBinding.bind(view);
        setListeners();
    }

    /**
     * Releases the binding so the view hierarchy can be garbage collected.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     *
     */
    private void setListeners() {
       // Listeners for all buttons
       binding.ackBtn.setOnClickListener(v -> openActivity(AcknowledgementsActivity.class));
       binding.aboutUsBtn.setOnClickListener(v -> openActivity(AboutUsActivity.class));
       binding.resyncBtn.setOnClickListener(v -> resyncData());
       binding.logoutBtn.setOnClickListener(v -> logout());
    }

    /**
     * TODO: create a logout function that terminates the firebase connection and returns to the login page
     */
    private void logout(){
        Log.d("INFO", "Logout Button Pressed");
    }

    /**
     *
     * @param destination
     */
    private void openActivity(Class<? extends Activity> destination) {
        startActivity(new Intent(requireContext(), destination));
    }

    /**
     * TODO : Javadoc
     */
    private void resyncData() {
        Log.d("INFO", "Resync Button Pressed");
    }

}
