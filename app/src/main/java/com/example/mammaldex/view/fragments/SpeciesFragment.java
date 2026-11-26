package com.example.mammaldex.view.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.mammaldex.MammalDexApp;
import com.example.mammaldex.R;
import com.example.mammaldex.databinding.FragmentHomeBinding;
import com.example.mammaldex.databinding.FragmentSpeciesBinding;
import com.example.mammaldex.model.database.entities.Species;
import com.example.mammaldex.view.SightedViewAdapter;
import com.example.mammaldex.viewModels.SpeciesViewModel;
import com.example.mammaldex.viewModels.SpeciesViewModelFactory;

public class SpeciesFragment extends Fragment {

    //A view model to access data from the database
    SpeciesViewModel viewModel;

    //The ID of the Species that is selected, also known as the Species Sci Name.
    private String speciesID;

    //Set the binding to the fragment XML elements.
    private FragmentSpeciesBinding binding;

    //Set the Sight Adapter
    SightedViewAdapter sightAdapter;


    /**
     * Fragment Constructor
     * @param species The ID of the species that is used to create the Fragment
     */
    public SpeciesFragment(String species){
        super(R.layout.fragment_species);
        setSpeciesID(species);

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
        binding = FragmentSpeciesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    /**
     * Run functions when the view is first created.
     * @param view The View returned by {onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set the adapter
        sightAdapter = new SightedViewAdapter();
        setRecyclerViewConfigs(view);

        //TODO USING TEST OBJECT
        sightAdapter.setTest();

        //Set the view model to connect to the database
        MammalDexApp app = (MammalDexApp) requireActivity().getApplication();
        viewModel = new ViewModelProvider(this, new SpeciesViewModelFactory(app.getRepository())).get(SpeciesViewModel.class);
        setSpeciesInfo(speciesID);

    }

    /**
     * A setter method that sets up the species name
     * @param speciesID The ID that is used to find the Species info from the database
     */
    public void setSpeciesInfo(String speciesID){
        try{
            viewModel.getSpeciesBySciName(speciesID).observe(getViewLifecycleOwner(), this::setSpeciesPage);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Cannot find Species in Database " + speciesID, Toast.LENGTH_SHORT).show();

            //Returns the user back to homepage
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new HomeFragment()).commit();
        }

    }


    /**
     * Sets the UI elements to show the data of the species.
     * @param speciesData The object that contains the data of the species from the database
     */
    private void setSpeciesPage(Species speciesData) {

        //Set the species data onto the view.
        binding.mammalName.setText(speciesData.getName());
        binding.mammalSciName.setText(speciesData.getSciName());

        //Set alternate names of the species
        viewModel.getSpeciesAltName(speciesData.getId()).observe(getViewLifecycleOwner(), alternateNames -> {

            //Boolean to check if List is truly empty
            boolean trulyEmpty = false;

            //if statement to fix a bug where isEmpty will always be false
            if(alternateNames.size() == 1){
                if(alternateNames.get(0).equals("")){
                    trulyEmpty = true;
                }
            }

            //Remove layout if species has no alternate names
            if(trulyEmpty){
                binding.mammalOtherNames.setVisibility(View.GONE);
                binding.knownAsText.setVisibility(View.GONE);
            }
            else{
                StringBuilder listOfAlt = new StringBuilder();

                //Format the Alternate Species names
                for(int i = 0; i < alternateNames.size(); i++){
                    listOfAlt.append(alternateNames.get(i));

                    //If last object, add no comma
                    if(i != (alternateNames.size() - 1)){
                        listOfAlt.append(", ");
                    }
                }

                //Insert list of Alternate names into View
                binding.mammalOtherNames.setText(listOfAlt);
            }
        });

        //Set the species wiki link
        binding.mammalWikiLink.setText(speciesData.getWikiLink());
        binding.mammalWikiLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent wikiIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(speciesData.getWikiLink()));
                    view.getContext().startActivity(wikiIntent);
                } catch (Exception e) {
                    Toast.makeText(view.getContext(), "Failed to load link", Toast.LENGTH_SHORT).show();
                }
            };
        });

        //Set the image of the species
        String endUrl = capitalize(speciesData.getSciName());
        // Define your Image URL or Resource
        String imageUrl = "file:///android_asset/downloaded_images/" + endUrl + ".jpg";

        // Load the image with Glide
        Glide.with(binding.getRoot().getContext())
                .load(imageUrl)
                .fallback(R.drawable.image_no_mammal)
                .placeholder(R.drawable.image_no_mammal)
                .into(binding.mammalImageView);

        // Open the Add Sighting popup.
        binding.addSightingButton.setOnClickListener(v -> {
            showAddSightingDialog(speciesData);
        });

        // Make the quick-add icon open the same popup.
        binding.quickAddButton.setOnClickListener(v -> {
            showAddSightingDialog(speciesData);
        });
    }
    /**
     * Displays a popup that allows the user to record a sighting
     * of the currently selected species.
     *
     * @param speciesData The species associated with the sighting.
     */
    private void showAddSightingDialog(Species speciesData) {

        // TODO: Display the sighting dialog and retrieve the user's location.

    }

    /**
     * Setter method to set the Fragment's Species ID
     * @param species
     */
    private void setSpeciesID(String species){
        speciesID = species;
    }


    /**
     * This method sets any configurations needed for the fragment's RecyclerView
     * during class instantiation.
     * @param view the root View of the fragment's layout
     */
    private void setRecyclerViewConfigs(View view) {
        // Creates a grid layout so the mammal cards are in two columns
        binding.sightedList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.sightedList.setAdapter(sightAdapter);
    }


    /**
     * Helper Function to capitalize a string
     * @param input A string that is being capitalized
     * @return The input capitalized
     */
    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }




}
