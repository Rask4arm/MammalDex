package com.example.mammaldex.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mammaldex.R;
import com.example.mammaldex.databinding.DexItemBinding;

import java.util.ArrayList;
import java.util.List;

public class DexViewAdapter extends RecyclerView.Adapter<DexViewAdapter.DexViewHolder> {

    private final List<DexObject> dexObjects = new ArrayList<>();
    private final List<String> testList = new ArrayList<>();

    @NonNull
    @Override
    public DexViewAdapter.DexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        //Create a View binding to the DexItem
        DexItemBinding binding = DexItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        //connect the binding to the view holder
        return new DexViewHolder(binding);
    }

    /**
     * Creates each item in the list
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull DexViewAdapter.DexViewHolder holder, int position) {

        String currentItem = testList.get(position);

        holder.binding.dexTitle.setText(currentItem);
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public void setOrderDex(List<String> dexList, View view){
        testList.addAll(dexList);
        notifyDataSetChanged();
    }

    static class DexViewHolder extends RecyclerView.ViewHolder {
        private final DexItemBinding binding;

        public DexViewHolder(@NonNull DexItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

