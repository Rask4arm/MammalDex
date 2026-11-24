package com.example.mammaldex.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mammaldex.databinding.SightedItemBinding;

import java.util.ArrayList;

public class SightedViewAdapter extends RecyclerView.Adapter<SightedViewAdapter.SightViewHolder> {

    public ArrayList<textSightObject> testList = new ArrayList<>();


    /**
     * Connects the binding onto the Sighting Item
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public SightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Create a View binding to the DexItem
        SightedItemBinding binding = SightedItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new SightViewHolder(binding);
    }

    /**
     * Inserts data onto the sighted item for the recycler view
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull SightViewHolder holder, int position) {

        //USING TEST OBJECT
        textSightObject currentSight = testList.get(position);

        int number = (position + 1);

        holder.binding.sightedNumber.setText("#" + number);
        holder.binding.sightedLocation.setText(currentSight.getCityText());
        holder.binding.sightedDate.setText(currentSight.getDate());
        holder.binding.sightedTime.setText(currentSight.getTime());
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    /**
     * An class object that represents the sighted item. Connect the binding onto the view holder.
     */
    public static class SightViewHolder extends RecyclerView.ViewHolder{

        private final SightedItemBinding binding;

        public SightViewHolder(@NonNull SightedItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    //CODE BELOW IS TEST FUNCTIONS

    /**
     * TODO: TEST OBJECT, MAY BE REMOVE FUTURE DATE
     */
    public static class textSightObject{
        public String cityText;
        public String date;
        public String time;

        public textSightObject(String cityText, String date, String time){
            this.cityText = cityText;
            this.date = date;
            this.time = time;
        }

        public String getCityText(){
            return cityText;
        }

        public String getDate(){
            return date;
        }

        public String getTime(){
            return time;
        }
    }

    public void setTest(){
        testList.add(new textSightObject("Ellensburg, WA", "11/24/26", "11:30"));
        testList.add(new textSightObject("Seattle, WA", "11/01/26", "1:54"));
        testList.add(new textSightObject("Paris, France", "12/08/04", "23:19"));
        testList.add(new textSightObject("Canada", "9/11/01", "15:42"));
        notifyDataSetChanged();
    }

}
