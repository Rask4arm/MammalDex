package com.example.mammaldex;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mammaldex.view.MammalViewAdapter;
import com.example.mammaldex.viewModels.SpeciesViewModel;

class ExampleFragment extends Fragment {
    public ExampleFragment() {
        super(R.layout.fragment_species_list);
    }
}
