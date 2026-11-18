package com.example.mammaldex.view;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
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
import com.example.mammaldex.databinding.FragmentSearchBinding;
import com.example.mammaldex.databinding.SearchFilterPopupBinding;
import com.example.mammaldex.model.database.entities.Species;
import com.example.mammaldex.viewModels.SpeciesViewModelFactory;
import com.example.mammaldex.viewModels.SpeciesViewModel;

import java.util.List;
import java.util.Objects;

public class SearchFragment extends Fragment {

    private PopupWindow filterWindow;
    private FragmentSearchBinding searchBinding;
    private SearchFilterPopupBinding filterBinding;
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
        searchBinding = FragmentSearchBinding.inflate(inflater, container, false);
        return searchBinding.getRoot();
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
        setSearchTextFilter(searchBinding.searchView);
        setSearchListeners();
        setFilterListeners();
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
    private void setSearchListeners() {
        // Listener for the search bar
        searchBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                           searchBinding.noResultsImage.setVisibility(View.VISIBLE);
                           searchBinding.noResultsText.setVisibility(View.VISIBLE);
                       } else {
                           searchBinding.noResultsImage.setVisibility(View.GONE);
                           searchBinding.noResultsText.setVisibility(View.GONE);
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
     * This method sets listeners for the filter button and its radio buttons in
     * the search bar.
     */
    private void setFilterListeners() {
        searchBinding.btnFilter.setOnClickListener(this::showFilterPopup);
    }

    /**
     * This method sets listeners for the radio buttons in the filter popup window
     * for the search bar.
     */
    private void setRadioListeners() {
        filterBinding.radioCommonName.setOnClickListener(this::filterCommonNames);
        filterBinding.radioSciName.setOnClickListener(this::filterSciNames);
        filterBinding.radioAltNames.setOnClickListener(this::filterAltNames);
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

    // TODO
    private void filterAltNames(View view) {
        // TODO : Logic

        // TODO : dismiss the filter popup after logic executes
    }

    // TODO
    private void filterCommonNames(View view) {
        // TODO : Logic

        // TODO : dismiss the filter popup after logic executes
    }

    // TODO
    private void filterSciNames(View view) {
        // TODO : Logic

        // TODO : dismiss the filter popup after logic executes
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
     * This method shows the filter popup window for the search bar
     * @param anchor the View (filter button) that owns the filter popup window
     */
    private void showFilterPopup(View anchor) {
        filterBinding = SearchFilterPopupBinding.inflate(LayoutInflater.from(anchor.getContext()));
        // Initializes filter popup window
        filterWindow = new PopupWindow(
                filterBinding.getRoot(),
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );
        // Sets method calls for radio options and displays filter window afterward
        setRadioListeners();
        filterWindow.showAsDropDown(anchor);
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
