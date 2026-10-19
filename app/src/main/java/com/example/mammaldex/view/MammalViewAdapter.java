package com.example.mammaldex.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mammaldex.R;
import com.example.mammaldex.model.database.entities.Species;

import java.util.List;
import java.util.Locale;

public class MammalViewAdapter extends RecyclerView.Adapter<MammalViewAdapter.SpeciesViewHolder> {
    private List<Species> speciesList;

    public MammalViewAdapter(List<Species> speciesList) {
        this.speciesList = speciesList;
    }

    @NonNull
    @Override
    public SpeciesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SpeciesViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.mammal_item, parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SpeciesViewHolder holder, int position) {
        Species currentSpecies = speciesList.get(position);
        holder.imageViewSpecies.setImageResource(R.drawable.ornithorhynchus_anatinus);
        holder.textViewName.setText(currentSpecies.getName());
        holder.textViewSciName.setText(currentSpecies.getSciName());

        String endUrl = capitalize(currentSpecies.getSciName().replace("_", " "));
        // Define your Image URL or Resource
        String imageUrl = "file:///android_asset/downloaded_images/" + endUrl + ".jpg";

        System.out.println(endUrl + "work");
        // Load the image with Glide
        Glide.with(holder.itemView.getContext())               // Pass context
                .load(imageUrl)           // Pass URL
                .into(holder.imageViewSpecies);         // Target ImageView
    }

    @Override
    public int getItemCount() {
        return speciesList.size();
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
        // Check for null or empty strings to prevent errors
        if (input == null || input.isEmpty()) {
            return input;
        }
        // Uppercase the first character and append the remaining string
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
