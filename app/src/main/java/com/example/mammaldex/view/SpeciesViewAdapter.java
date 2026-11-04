package com.example.mammaldex.view;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mammaldex.R;
import com.example.mammaldex.model.database.entities.Species;

import java.util.ArrayList;
import java.util.List;

public class SpeciesViewAdapter extends RecyclerView.Adapter<SpeciesViewAdapter.SpeciesViewHolder> {

    private final List<Species> species = new ArrayList<>();

    public void setSpecies(List<Species> newSpecies) {
        species.clear();
        species.addAll(newSpecies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SpeciesViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.species_item, parent, false);

        return new SpeciesViewHolder(view);
    }


    /**
     * Sets the species info to the species card
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull SpeciesViewHolder holder, int position) {
        Species currentSpecies = species.get(position);

        //Set the species name and scientific name
        holder.textViewName.setText(currentSpecies.getName());
        holder.textViewSciName.setText(currentSpecies.getSciName());

        String endUrl = capitalize(currentSpecies.getSciName().replace("_", " "));
        // Define your Image URL or Resource
        String imageUrl = "file:///android_asset/downloaded_images/" + endUrl + ".jpg";

        //Set the species wikipedia link
        holder.wikiIconImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent wikiIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentSpecies.getWikiLink()));
                    view.getContext().startActivity(wikiIntent);
                } catch (Exception e) {
                    Toast.makeText(view.getContext(), "Failed to load link", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Load the image with Glide
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.imageViewSpecies);
    }

    @Override
    public int getItemCount() {
        return species.size();
    }

    /**
     * The Object that contains the species info on the Search Page
     */
    static class SpeciesViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewSpecies;
        TextView textViewName;
        TextView textViewSciName;
        ImageView wikiIconImg;

        public SpeciesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSpecies = itemView.findViewById(R.id.mammalImageView);
            textViewName = itemView.findViewById(R.id.mammalName);
            textViewSciName = itemView.findViewById(R.id.mammalSciName);
            wikiIconImg = itemView.findViewById(R.id.mammalWikiLink);
        }
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