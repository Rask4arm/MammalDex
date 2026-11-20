package com.example.mammaldex.view;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.example.mammaldex.MammalDexApp;
import com.example.mammaldex.R;
import com.example.mammaldex.databinding.FragmentSearchBinding;
import com.example.mammaldex.databinding.SearchFilterPopupBinding;
import com.example.mammaldex.viewModels.SpeciesViewModelFactory;
import com.example.mammaldex.viewModels.SpeciesViewModel;

public class SearchFragment extends Fragment {

    private PopupWindow filterWindow;
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
        setFields();
        setSearchTextFilter(binding.searchView);
        setListeners();
        setRecyclerViewConfigs(view);
        setViewModelConfigs();
    }

    /**
     * This method sets any uninstantiated class fields during class instantiation.
     */
    private void setFields() {
        adapter = new SearchViewAdapter(item -> {
            goToSpeciesFragment(item.getSciName());
        }, species -> {
            viewModel.setSpeciesAsSeen(species.getSciName());
        }, species -> {
            viewModel.setSpeciesAsNotSeen(species.getSciName());
        });
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
    private void setListeners() {
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
                viewModel.setSearchText(searchText);
                return true;
            }
        });
    }

    /**
     * This method sets any configurations needed for the fragment's RecyclerView
     * during class instantiation.
     * @param view the root View of the fragment's layout
     */
    private void setRecyclerViewConfigs(View view) {
        // Creates a grid layout so the mammal cards are in two columns
        binding.speciesList.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        binding.speciesList.setAdapter(adapter);
    }

    /**
     *  This method sets any configurations needed for the viewModel during
     *  class instantiation.
     */
    private void setViewModelConfigs() {
        viewModel.getDisplayedSpecies().observe(getViewLifecycleOwner(), results -> {
            if (results != null) {
                if (results.isEmpty()) {
                    binding.noResultsImage.setVisibility(View.VISIBLE);
                    binding.noResultsText.setVisibility(View.VISIBLE);
                } else {
                    binding.noResultsImage.setVisibility(View.GONE);
                    binding.noResultsText.setVisibility(View.GONE);
                }
                adapter.setSpecies(results);
            }
        });
    }

    /**
     * This method shows the filter popup window for the search bar
     * @param anchor the View (filter button) that owns the filter popup window
     */
    private void showFilterPopup(View anchor) {
        // Initializes filter popup window
        filterWindow = new PopupWindow(
                SearchFilterPopupBinding.inflate(LayoutInflater.from(anchor.getContext())).getRoot(),
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );
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
