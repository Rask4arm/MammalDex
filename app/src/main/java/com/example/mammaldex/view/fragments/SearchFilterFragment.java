package com.example.mammaldex.view.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mammaldex.MammalDexApp;
import com.example.mammaldex.viewModels.SpeciesViewModel;
import com.example.mammaldex.viewModels.SpeciesViewModelFactory;

public class SearchFilterFragment extends Fragment {

//    private FragmentSearchFilterBinding binding;
    private SpeciesViewModel viewModel;

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
        setListeners();
    }

    /**
     * This method sets any needed UI listeners during class instantiation.
     */
    private void setListeners() {
//        binding.btnApply.setOnClickListener(this::filterResults);
//        binding.btnCloseFilter.setOnClickListener(v -> finish());
    }

    /**
     * This method sets any uninstantiated class fields during class instantiation.
     */
    private void setFields() {
        MammalDexApp app = (MammalDexApp) requireActivity().getApplication();
        viewModel = new ViewModelProvider(this, new SpeciesViewModelFactory(app.getRepository())).get(SpeciesViewModel.class);
    }


    private void disableDropdown() {

    }
    

    private void filterResults() {
//        // Sets the selected filter options
//        String orderText = binding.orderText.getText().toString();
//        String familyText = binding.familyText.getText().toString();
//        String genusText = binding.genusText.getText().toString();
//        // Filters search results by lowest filter category
//        if (!orderText.isEmpty()) {
//
//        } else if (!familyText.isEmpty()) {
//
//        } else if (!genusText.isEmpty()) {
//
//        } else {   // Filters are empty; revert results to what the search text contains
//
//        }
    }

}
