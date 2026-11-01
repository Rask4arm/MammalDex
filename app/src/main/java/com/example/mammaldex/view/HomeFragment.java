package com.example.mammaldex.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mammaldex.R;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    /**
     * TODO : Add Javadoc descriptinn
     * @param view The View returned by onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO : Add comment
        TextView homeMessage =
                view.findViewById(R.id.homeMessage);
        Button btnNext =
                view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v ->
                Navigation.findNavController(v)
                        .navigate(R.id.action_home_to_second)
        );
    }
}
