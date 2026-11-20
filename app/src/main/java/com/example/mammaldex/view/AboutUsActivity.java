package com.example.mammaldex.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mammaldex.databinding.ActivityAboutUsBinding;

public class AboutUsActivity extends AppCompatActivity {

    private ActivityAboutUsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
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
