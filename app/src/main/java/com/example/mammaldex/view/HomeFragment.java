package com.example.mammaldex.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mammaldex.MammalDexApp;
import com.example.mammaldex.R;
import com.example.mammaldex.databinding.FragmentHomeBinding;
import com.example.mammaldex.databinding.FragmentSearchBinding;
import com.example.mammaldex.viewModels.SpeciesViewModel;
import com.example.mammaldex.viewModels.SpeciesViewModelFactory;

public class HomeFragment extends Fragment {

    private DexViewAdapter dexAdapter;
    private SpeciesViewModel viewModel;

    private FragmentHomeBinding binding;

    public HomeFragment() {
        super(R.layout.fragment_home);
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
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    /**
     * TODO : Add Javadoc descriptinn
     * @param view The View returned by onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Connects the adapter
        dexAdapter = new DexViewAdapter();
        setRecyclerViewConfigs(view);

        //Set the view model to connect to the database
        MammalDexApp app = (MammalDexApp) requireActivity().getApplication();
        viewModel = new ViewModelProvider(this, new SpeciesViewModelFactory(app.getRepository())).get(SpeciesViewModel.class);
        setViewModelConfigs(view);

    }

    /**
     * This method sets any configurations needed for the fragment's RecyclerView
     * during class instantiation.
     * @param view the root View of the fragment's layout
     */
    private void setRecyclerViewConfigs(View view) {
        // Creates a grid layout so the mammal cards are in two columns
        binding.dexList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.dexList.setAdapter(dexAdapter);
    }

    private void setViewModelConfigs(View view) {
        viewModel.getDistinctOrders().observe(getViewLifecycleOwner(), results -> {
                dexAdapter.setOrderDex(results, view);
            }
        );
    }

}
