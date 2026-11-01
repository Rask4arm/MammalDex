package com.example.mammaldex.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.example.mammaldex.MammalDexApp;
import com.example.mammaldex.R;
import com.example.mammaldex.model.Repository;
import com.example.mammaldex.viewModels.SpeciesViewModelFactory;
import com.example.mammaldex.viewModels.SpeciesViewModel;

import java.util.List;

public class SecondFragment extends Fragment {

    private SearchView searchView;
    private SpeciesViewModel viewModel;
    private SpeciesViewAdapter adapter;

    public SecondFragment() {
        super(R.layout.fragment_second);
    }

    /**
     * ???
     * @param view The View returned by onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO : Add comment
        RecyclerView recyclerView = view.findViewById(R.id.species_list);
        Button btnHome = view.findViewById(R.id.btnHome);
        searchView = view.findViewById(R.id.search_view);
        // TODO : Add comment
        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );
        // TODO : Add comment
        adapter = new SpeciesViewAdapter();
        recyclerView.setAdapter(adapter);
        MammalDexApp app = (MammalDexApp) requireActivity().getApplication();
        // TODO : Add comment
        viewModel = new ViewModelProvider(this, new SpeciesViewModelFactory(app.getRepository())).get(SpeciesViewModel.class);
        viewModel.getSpecies().observe(getViewLifecycleOwner(),
                species -> adapter.setSpecies(species)
        );
        // TODO : Add comment
        btnHome.setOnClickListener(v ->
                Navigation.findNavController(v)
                        .popBackStack()
        );
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * This method executes the included code when the Enter button is pressed.
             * @param query the query text that is to be submitted
             * @return false to notify SearchView to handle Enter press with default procedure
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO : Add any logic needed for after the Enter button is pressed
                return false;
            }

            /**
             *
             * @param newText the new content of the query text field.
             * @return
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                searchSpecies(newText);
                return true;
            }
        });
    }

    /**
     * TODO : Add Javadoc
     * @param newText ???
     */
    public void searchSpecies(String newText) {
        // TODO : Need to get access to the getSpeciesNamesWith(String search)
//        LiveData<List<String>> species = new Repository().getSpeciesNamesWith(newText);

    }
}
