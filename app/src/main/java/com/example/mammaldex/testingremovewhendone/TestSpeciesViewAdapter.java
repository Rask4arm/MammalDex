package com.example.mammaldex.testingremovewhendone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mammaldex.R;
import com.example.mammaldex.model.database.entities.Species;

import java.util.ArrayList;
import java.util.List;

public class TestSpeciesViewAdapter
        extends RecyclerView.Adapter<TestSpeciesViewAdapter.SpeciesViewHolder> {

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
        holder.imageViewSpecies.setImageResource(R.drawable.ornithorhynchus_anatinus);
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
}