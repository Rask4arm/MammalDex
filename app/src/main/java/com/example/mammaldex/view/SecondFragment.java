package com.example.mammaldex.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mammaldex.MammalDexApp;
import com.example.mammaldex.R;
import com.example.mammaldex.viewModels.SpeciesViewModelFactory;
import com.example.mammaldex.viewModels.SpeciesViewModel;

public class SecondFragment extends Fragment {

    private SpeciesViewModel viewModel;
    private SpeciesViewAdapter adapter;

    public SecondFragment() {
        super(R.layout.fragment_second);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView =
                view.findViewById(R.id.species_list);

        Button btnHome =
                view.findViewById(R.id.btnHome);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        adapter = new SpeciesViewAdapter();

        recyclerView.setAdapter(adapter);

        MammalDexApp app = (MammalDexApp) requireActivity().getApplication();

        viewModel = new ViewModelProvider(this, new SpeciesViewModelFactory(app.getRepository())).get(SpeciesViewModel.class);

        viewModel.getSpecies().observe(getViewLifecycleOwner(),
                species -> adapter.setSpecies(species)
        );

        btnHome.setOnClickListener(v ->
                Navigation.findNavController(v)
                        .popBackStack()
        );
    }
}
