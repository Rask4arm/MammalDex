package com.example.mammaldex.view;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.example.mammaldex.MammalDexApp;
import com.example.mammaldex.R;
import com.example.mammaldex.model.database.entities.Species;
import com.example.mammaldex.viewModels.SpeciesViewModelFactory;
import com.example.mammaldex.viewModels.SpeciesViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private Button btnHome;
    private ImageView noResultsImage;
    private List<Species> allSpecies = new ArrayList<>();
    private SpeciesViewAdapter adapter;
    private SearchView searchView;
    private SpeciesViewModel viewModel;
    private TextView noResultsText;

    public SearchFragment() {
        super(R.layout.fragment_search);
    }

    /**
     * This method handles all operations needed for the fragment to function,
     * including setting class fields, UI listeners, configuring the corresponding
     * view's RecyclerView, and observing all species data in the local database.
     * @param view The View returned by onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFields(view);
        setSearchTextFilter(searchView);
        setUIListeners();
        setRecyclerViewConfigs(view);
        observeAllSpecies();
    }

    /**
     * This method sets any uninstantiated class fields during class instantiation.
     * @param view the root View of the fragment's layout
     */
    private void setFields(View view) {
        adapter = new SpeciesViewAdapter();
        btnHome = view.findViewById(R.id.btnHome);
        noResultsImage = view.findViewById(R.id.no_results_image);
        noResultsText = view.findViewById(R.id.no_results_text);
        searchView = view.findViewById(R.id.search_view);
        MammalDexApp app = (MammalDexApp) requireActivity().getApplication();
        viewModel = new ViewModelProvider(this, new SpeciesViewModelFactory(app.getRepository())).get(SpeciesViewModel.class);
    }

    /**
     * This method attaches text filters to the search bar.
     * @param searchView the search bar's SearchView object
     */
    private void setSearchTextFilter(SearchView searchView) {
        // Gets the internal EditText object of the SearchView
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(45)
        });
    }

    /**
     * This method sets any needed UI listeners during class instantiation.
     */
    private void setUIListeners() {
        btnHome.setOnClickListener(v ->
                Navigation.findNavController(v)
                        .popBackStack()
        );
        // Listener for the search bar
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * This method executes the included code when the Enter button is pressed,
             * which includes closing the on-screen keyboard
             * @param query the query text that is to be submitted
             * @return false to notify SearchView to handle Enter press with default procedure
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            /**
             * This method executes the included code when the user changes the
             * text input of the search bar; encompasses insertions, deletions,
             * and replacements.
             * @param searchText the new content of the query text field.
             * @return true
             */
            @Override
            public boolean onQueryTextChange(String searchText) {
                LiveData<List<Species>> filtered = viewModel.getSpeciesNamesWith("%" + searchText + "%");
                // TODO : ISSUE -> Makes a new observer every keystroke, does not destroy previous observers
                filtered.observe(getViewLifecycleOwner(), species -> {
                   if (species != null) {
                       adapter.setSpecies(species);
                       // Displays content when there are no search results; hides if there are
                       if (species.isEmpty()) {
                           noResultsImage.setVisibility(View.VISIBLE);
                           noResultsText.setVisibility(View.VISIBLE);
                       } else {
                           noResultsImage.setVisibility(View.GONE);
                           noResultsText.setVisibility(View.GONE);
                       }
                   }
                });
                return true;
            }
        });
    }

    /**
     * This method sets any configurations for the fragment's RecyclerView
     * during class instantiation.
     * @param view the root View of the fragment's layout
     */
    private void setRecyclerViewConfigs(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.species_list);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );
        // Creates a grid layout so the mammal cards are in two columns
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        recyclerView.setAdapter(adapter);
    }

    /**
     * This method observes the data for all species in the MammalDex database during the
     * fragment's initialization, updates the species in the SpeciesViewAdapter, and stores
     * all Species objects in the "allSpecies" field of type List<Species>. This allows for
     * multiple searches to be performed for the available species without reloading the
     * data multiple times.
     */
    private void observeAllSpecies() {
        viewModel.getAllSpecies().observe(getViewLifecycleOwner(), species -> {
                    adapter.setSpecies(species);
                    allSpecies = (species != null) ? species : new ArrayList<>();
                }
        );
    }

}
