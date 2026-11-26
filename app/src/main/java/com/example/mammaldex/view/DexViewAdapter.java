package com.example.mammaldex.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mammaldex.R;
import com.example.mammaldex.databinding.DexItemBinding;
import com.example.mammaldex.databinding.DexSpeciesItemBinding;
import com.example.mammaldex.model.database.entities.Species;

import java.util.ArrayList;
import java.util.List;

public class DexViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<DexObject> dexObjects = new ArrayList<>();

    private final OnDexClickListener itemListener;
    private final OnSpeciesClickListener speciesListener;

    public DexViewAdapter(OnDexClickListener itemListener, OnSpeciesClickListener speciesListener){
        this.itemListener = itemListener;
        this.speciesListener = speciesListener;
    }

    /**
     * Listener interface for handling clicks on species items.
     */
    public interface OnDexClickListener {
        void onItemClick(DexObject dexObject);
    }
    public interface OnSpeciesClickListener{
        void onClickSpecies(DexObject dexObject);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        //If the dexObject is a species, create Species Dex Object.
        if(viewType == 1){
            DexSpeciesItemBinding binding = DexSpeciesItemBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false
            );

            //Connect the binding to the view holder
            return new SpeciesDexViewHolder(binding);
        }


        //Create a view binding to the Dex Item
        DexItemBinding binding = DexItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        //connect the binding to the view holder
        return new DexViewHolder(binding);
    }

    /**
     * A function that checks if the current dexObject is a species or not
     * @param position position to query
     * @return 1 for being a species, 0 if not
     */
    @Override
    public int getItemViewType(int position){
        //Get the current object
        DexObject currentItem = dexObjects.get(position);

        if(currentItem.getLevel() == 4){
            //If a species, return 1
            return 1;
        }
        else{
            //If a taxonomic rank, return 0
            return 0;
        }
    }

    /**
     * Creates each item in the list
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DexViewHolder){
            bindDexViewHolder((DexViewHolder) holder, position);
        }
        else{
            bindSpeciesViewHolder((SpeciesDexViewHolder) holder, position);
        }

    }

    /**
     * Binds the taxonomic rank info onto the dex object.
     * @param holder The XML that we are binding the taxonomic rank info to
     * @param position for dexObjects to get the taxonomic rank info
     */
    public void bindDexViewHolder(DexViewHolder holder, int position){

        //Get the current object containing the taxonomic rank info
        DexObject currentItem = dexObjects.get(position);

        //A dp to be added to the buffer
        float bufferDp = 0;

        //Place the title of taxonomic rank to the object
        holder.binding.dexTitle.setText(currentItem.getDexTitle());

        //Set the buffer view dp depending on level in dex
        if(currentItem.getLevel() == 2) {
            bufferDp = 20f;
        } else if (currentItem.getLevel() == 3) {
            bufferDp = 40f;
        }
        else{
            bufferDp = 0;
        }

        //Apply the buffer view depending on level
        holder.binding.bufferView.getLayoutParams().width = (int) dpToPx(bufferDp, holder.itemView.getContext());


        //Add a Click Listener to the dex item to expand the next level in taxonomic
        holder.binding.dexItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                    if(currentItem.isExpanded()){
                        //Set expanded as false
                        currentItem.setExpanded(false);
                    }
                    else{
                        //Set expanded as true
                        currentItem.setExpanded(true);
                    }

                    //Set the listener function
                    itemListener.onItemClick(currentItem);

                } catch (Exception e) {
                    Toast.makeText(view.getContext(), "Failed to Expand", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Binds the taxonomic rank info onto the dex object.
     * @param holder The XML that we are binding the taxonomic rank info to
     * @param position for dexObjects to get the taxonomic rank info
     */
    public void bindSpeciesViewHolder(SpeciesDexViewHolder holder, int position){

        //Get the current object containing the taxonomic rank info
        DexObject currentItem = dexObjects.get(position);

        //Get the species object from Dex object
        Species currentSpecies = currentItem.getSpecies();

        //Get the species URL for the image
        String endUrl = capitalize(currentSpecies.getSciName());
        String imageUrl = "file:///android_asset/downloaded_images/" + endUrl + ".jpg";

        //Place the title of taxonomic rank to the object
        holder.binding.speciesName.setText(currentSpecies.getName());

        //Load the image of the species with glide
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.binding.speciesImage);



        //Add a Click Listener to the navigate to species page
        holder.binding.speciesDex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    //Set the listener function
                    speciesListener.onClickSpecies(currentItem);

                } catch (Exception e) {
                    Toast.makeText(view.getContext(), "Failed to Navigate", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    /**
     * Get the amount of objects in list
     * @return amount of objects in list
     */
    @Override
    public int getItemCount() {
        return dexObjects.size();
    }

    /**
     * Implements the first level of the dex, being the orders
     * @param orderList the list of orders
     * @param view the view of the application
     */
    public void setOrderDex(List<String> orderList, View view){

        for(String dexItem : orderList){
            DexObject newDexObject = new DexObject(dexItem, dexItem, 1, false);
            dexObjects.add(newDexObject);
        }
        notifyDataSetChanged();
    }

    /**
     * Expands the dex object to show the next level of taxonomic rank
     * @param parentDex the current level that is being expanded
     * @param newList a list of the next level of current dex object
     */
    public void expandDex(DexObject parentDex, List<String> newList){

        //Create a list of the next level
        List<DexObject> nextLevel = new ArrayList<>();

        //Get parents taxonomic rank info
        int insertPosition = dexObjects.indexOf(parentDex) + 1;
        int parentLevel = parentDex.getLevel();

        //Load new objects onto new list
        for(String newDexItem : newList){
            DexObject newDexObject = new DexObject(newDexItem, newDexItem, (parentLevel+1), false);
            nextLevel.add(newDexObject);
        }

        //Add the next level objects onto the dexObject List
        dexObjects.addAll(insertPosition, nextLevel);

        //Updates the RecyclerView
        notifyItemRangeInserted(insertPosition, nextLevel.size());

    }

    /**
     * Expands the dex object to show the species level of current genus
     * @param parentDex the current level that is being expanded
     * @param newList a list of the next level of current dex object
     */
    public void expandSpecies(DexObject parentDex, List<Species> newList){

        //Create a list of the next level
        List<DexObject> nextLevel = new ArrayList<>();

        //Get parents taxonomic rank info
        int insertPosition = dexObjects.indexOf(parentDex) + 1;

        //Load new objects onto new list
        for(Species speciesInfo : newList){
            DexObject newDexObject = new DexObject(speciesInfo.getSciName(), speciesInfo.getName(), 4, false);
            newDexObject.setSpecies(speciesInfo);
            nextLevel.add(newDexObject);
        }

        //Add the next level objects onto the dexObject List
        dexObjects.addAll(insertPosition, nextLevel);

        //Updates the RecyclerView
        notifyItemRangeInserted(insertPosition, nextLevel.size());

    }

    /**
     * Removes dexObjects that's nested of current object
     * @param parentDex The dex object that we are de-expanding
     */
    public void deExpandDex(DexObject parentDex){

        //Get parents info
        int insertPosition = dexObjects.indexOf(parentDex) + 1;

        //Variables for to keep track how many dex items are removed
        boolean isChild = true;
        int currentPosition = insertPosition;
        int removeItemAmt = 0;

        //Until we reach the another dex object of the same level or lower, add to remove count
        while(isChild){
            if(dexObjects.get(currentPosition).getLevel() > parentDex.getLevel()){
                removeItemAmt++;
                currentPosition++;
            }
            else{
                isChild = false;
            }
        }

        //Remove the next levels objects
        dexObjects.subList(insertPosition, (insertPosition+removeItemAmt)).clear();

        //Updates the RecyclerView
        notifyItemRangeRemoved(insertPosition, removeItemAmt);

    }

    /**
     * Helper function that converts a DP number into pixels
     * @param neededDp A float that represents the target dp
     * @param context the context of the view
     * @return A float of pixels that represents the dp
     */
    public static float dpToPx(Float neededDp, Context context){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, neededDp,
                context.getResources().getDisplayMetrics());
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

    public static class DexViewHolder extends RecyclerView.ViewHolder {
        private final DexItemBinding binding;

        public DexViewHolder(@NonNull DexItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class SpeciesDexViewHolder extends RecyclerView.ViewHolder{
        private final DexSpeciesItemBinding binding;

        public SpeciesDexViewHolder(@NonNull DexSpeciesItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

