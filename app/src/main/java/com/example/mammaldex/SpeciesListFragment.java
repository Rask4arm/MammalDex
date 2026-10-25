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

public class SpeciesListFragment extends Fragment {
    SpeciesViewModel speciesViewModel;
    MammalViewAdapter mammalViewAdapter;

    public SpeciesListFragment() {
        super(R.layout.fragment_species_list);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.species_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        MammalDexApp app = (MammalDexApp) requireActivity().getApplication();

        speciesViewModel = new ViewModelProvider(this, new SpeciesViewModelFactory(app.getRepository())).get(SpeciesViewModel.class);

        mammalViewAdapter = new MammalViewAdapter(species -> {
            Bundle args = new Bundle();
            args.putLong("id", species.getId());

            //NavHostFragment.findNavController(this).navigate(R.id.speciesListFragment, args);
        });

        recyclerView.setAdapter(mammalViewAdapter);

        speciesViewModel.getSpecies().observe(getViewLifecycleOwner(), species -> mammalViewAdapter.submitList(species));


    }
}
