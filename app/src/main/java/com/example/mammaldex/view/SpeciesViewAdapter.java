package com.example.mammaldex.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    public void onBindViewHolder(@NonNull SpeciesViewHolder holder, int position) {
        Species currentSpecies = species.get(position);

        holder.textViewName.setText(currentSpecies.getName());
        holder.textViewSciName.setText(currentSpecies.getSciName());

        String endUrl = capitalize(currentSpecies.getSciName().replace("_", " "));
        // Define your Image URL or Resource
        String imageUrl = "file:///android_asset/downloaded_images/" + endUrl + ".jpg";

        // Load the image with Glide
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.imageViewSpecies);
    }

    @Override
    public int getItemCount() {
        return species.size();
    }

    static class SpeciesViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewSpecies;
        TextView textViewName;
        TextView textViewSciName;

        public SpeciesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSpecies = itemView.findViewById(R.id.mammalImageView);
            textViewName = itemView.findViewById(R.id.mammalName);
            textViewSciName = itemView.findViewById(R.id.mammalSciName);
        }
    }

    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}