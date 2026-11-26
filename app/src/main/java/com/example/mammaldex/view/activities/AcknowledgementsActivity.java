package com.example.mammaldex.view.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mammaldex.databinding.ActivityAcknowledgementsBinding;

public class AcknowledgementsActivity extends AppCompatActivity {

    private ActivityAcknowledgementsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAcknowledgementsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    /**
     *
     */
    private void setListeners() {
        binding.backBtn.setOnClickListener(v -> finish());
    }

}
