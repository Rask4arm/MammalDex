package com.example.mammaldex.view;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.example.mammaldex.MammalDexApp;
import com.example.mammaldex.R;
import com.example.mammaldex.databinding.ActivityMainBinding;
import com.example.mammaldex.databinding.FragmentHomeBinding;
import com.example.mammaldex.databinding.FragmentSearchBinding;
import com.example.mammaldex.model.database.entities.Species;
import com.example.mammaldex.viewModels.SpeciesViewModelFactory;
import com.example.mammaldex.viewModels.SpeciesViewModel;

import java.util.List;
import java.util.Objects;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private SearchViewAdapter adapter;
    private SpeciesViewModel viewModel;


    public SearchFragment() {
        super(R.layout.fragment_search);
    }

    /**
     * This method handles all operations needed for when the fragment is being
     * creating, including initializing the binding variable for UI references.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return the top-layer View of the inflated layout (the outermost element of the
     * associated XML file).
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                         ViewGroup container,
                         Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * This method handles all operations needed for the fragment to function,
     * including setting class fields, UI listeners, configuring the corresponding
     * view's RecyclerView, and observing all species data in the local database.
     * @param view The View returned by onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFields(view);
        setSearchTextFilter(binding.searchView);
        setUIListeners();
        setRecyclerViewConfigs(view);
        observeAllSpecies();
    }

    /**
     * This method sets any uninstantiated class fields during class instantiation.
     * @param view the root View of the fragment's layout
     */
    private void setFields(View view) {
        adapter = new SearchViewAdapter(item -> {
            goToSpeciesFragment(item.getSciName());
        }, species -> {
            viewModel.setSpeciesAsSeen(species.getSciName());
        }, species -> {
            viewModel.setSpeciesAsNotSeen(species.getSciName());
        });
        noResultsImage = view.findViewById(R.id.noResultsImage);
        noResultsText = view.findViewById(R.id.noResultsText);
        searchView = view.findViewById(R.id.searchView);
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
        // Listener for the search bar
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
             * @param searchText the new content of the query text field
             * @return true to notify SearchView that the event was fully handled
             */
            @Override
            public boolean onQueryTextChange(String searchText) {
                LiveData<List<Species>> filtered = viewModel.getSortedSpeciesNamesWith("%" + searchText + "%");
                // TODO : ISSUE -> Makes a new observer every keystroke, does not destroy previous observers
                // Gets the matched species of type List<Species>
                filtered.observe(getViewLifecycleOwner(), results -> {
                   if (results != null) {
                       // Displays content when there are no search results; hides if there are results
                       if (results.isEmpty()) {
                           binding.noResultsImage.setVisibility(View.VISIBLE);
                           binding.noResultsText.setVisibility(View.VISIBLE);
                       } else {
                           binding.noResultsImage.setVisibility(View.GONE);
                           binding.noResultsText.setVisibility(View.GONE);
                       }
                       // Swaps in and highlights the alternative names of the mammal's to indicate
                       // when those names produced matches
                       // TODO : Need to find a way to change the contents of MaterialCardView objects instead of Species
                       adapter.setSpecies(results);
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
        RecyclerView recyclerView = view.findViewById(R.id.speciesList);

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
                }
        );
    }

    /**
     * Navigates to the Species page depending on selected species.
     * @param speciesId
     */
    protected void goToSpeciesFragment(String speciesId){

        try {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new SpeciesFragment(speciesId)).commit();
        } catch (Exception e) {
            System.out.println("Nope" + e);
            throw new RuntimeException(e);
        }

    }

}
