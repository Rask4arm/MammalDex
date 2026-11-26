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

import com.example.mammaldex.R;
import com.example.mammaldex.databinding.DexItemBinding;
import com.example.mammaldex.model.database.entities.Species;

import java.util.ArrayList;
import java.util.List;

public class DexViewAdapter extends RecyclerView.Adapter<DexViewAdapter.DexViewHolder> {

    private final List<DexObject> dexObjects = new ArrayList<>();

    private final OnItemClickListener itemListener;

    public DexViewAdapter(OnItemClickListener itemListener){
        this.itemListener = itemListener;
    }

    /**
     * Listener interface for handling clicks on species items.
     */
    public interface OnItemClickListener {
        void onItemClick(DexObject dexObject);
    }

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
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull DexViewAdapter.DexViewHolder holder, int position) {

        //Get the current object
        DexObject currentItem = dexObjects.get(position);

        //A dp to be added to the buffer
        float bufferDp = 0;

        //Place the text of the object
        holder.binding.dexTitle.setText(currentItem.getDexTitle());

        //Set the buffer view dp depending on level
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


        //Add a Click Listener to the dex item to see the next level
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

                    itemListener.onItemClick(currentItem);

                } catch (Exception e) {
                    Toast.makeText(view.getContext(), "Failed to Expand", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dexObjects.size();
    }

    /**
     * Implements the first layer of the dex, being the orders
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
     * Expands the dex object to show the next level of mammal biology
     * @param parentDex the current level that is being expanded
     * @param newList a list of the next level of current dex object
     */
    public void expandDex(DexObject parentDex, List<String> newList){

        //Create a list of the next level
        List<DexObject> nextLevel = new ArrayList<>();

        //Get parents info
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

    static class DexViewHolder extends RecyclerView.ViewHolder {
        private final DexItemBinding binding;

        public DexViewHolder(@NonNull DexItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

