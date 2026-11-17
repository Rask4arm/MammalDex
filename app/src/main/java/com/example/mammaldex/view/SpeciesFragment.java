package com.example.mammaldex.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.mammaldex.MammalDexApp;
import com.example.mammaldex.R;
import com.example.mammaldex.model.database.entities.Species;
import com.example.mammaldex.viewModels.SpeciesViewModel;
import com.example.mammaldex.viewModels.SpeciesViewModelFactory;

import java.util.Objects;

public class SpeciesFragment extends Fragment {

    //A view model to access data from the database
    SpeciesViewModel viewModel;

    //The ID of the Species that is selected, also known as the Species Sci Name.
    private String speciesID;


    /**
     * Fragment Constructor
     * @param species The ID of the species that is used to create the Fragment
     */
    public SpeciesFragment(String species){
        super(R.layout.fragment_species);
        setSpeciesID(species);

    }

    /**
     * Run functions when the view is first created.
     * @param view The View returned by {onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MammalDexApp app = (MammalDexApp) requireActivity().getApplication();
        viewModel = new ViewModelProvider(this, new SpeciesViewModelFactory(app.getRepository())).get(SpeciesViewModel.class);
        setSpeciesInfo(view, speciesID);
    }

    /**
     * A setter method that sets up the species name
     * @param speciesID The ID that is used to find the Species info from the database
     */
    public void setSpeciesInfo(View view ,String speciesID){
        try{
            viewModel.getSpeciesBySciName(speciesID).observe(getViewLifecycleOwner(), speciesData ->{
                setSpeciesPage(view, speciesData);
            });
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Cannot find Species in Database " + speciesID, Toast.LENGTH_SHORT).show();

            //Returns the user back to homepage
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new HomeFragment()).commit();
        }

    }


    /**
     * Sets the UI elements to show the data of the species.
     * @param view The Current view of the application
     * @param speciesData The object that contains the data of the species from the database
     */
    private void setSpeciesPage(View view, Species speciesData) {

        TextView nameView = view.findViewById(R.id.mammalName);

        try{
            nameView.setText(speciesData.getName());
        } catch (Exception e) {
            Toast.makeText(getActivity(), "getName didnt work " + speciesData, Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * Setter method to set the Fragment's Species ID
     * @param species
     */
    private void setSpeciesID(String species){
        speciesID = species;
    }




}
