package com.example.mammaldex.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mammaldex.R;
import com.example.mammaldex.model.database.entities.Species;

import java.util.List;
import java.util.Objects;

public class MammalViewAdapter extends ListAdapter<Species, MammalViewAdapter.SpeciesViewHolder> {
    private List<Species> speciesList;

    public interface onSpeciesClick {
        void onClick(Species species);
    }

    private final onSpeciesClick listener;

    public MammalViewAdapter(onSpeciesClick listener) {
        super(new DiffUtil.ItemCallback<Species>() {
            @Override
            public boolean areItemsTheSame(Species a, Species b) {
                return a.getId().equals(b.getId());
            }

            @Override
            public boolean areContentsTheSame(Species a, Species b) {
                return a.getId().equals(b.getId()) && Objects.equals(a.getSeen(), b.getSeen());
            }
        });

        this.listener = listener;
    }

    @NonNull
    @Override
    public SpeciesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SpeciesViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.species_item, parent,false)
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

        // Load the image with Glide
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.imageViewSpecies);
        holder.itemView.setOnClickListener(v -> listener.onClick(currentSpecies));

    }

    @Override
    public int getItemCount() {
        return speciesList.size();
    }

    static class SpeciesViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageViewSpecies;
        final TextView textViewName;
        final TextView textViewSciName;


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
