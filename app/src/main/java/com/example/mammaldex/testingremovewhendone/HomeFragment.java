package com.example.mammaldex.testingremovewhendone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mammaldex.R;

public class HomeFragment extends Fragment {

    private MainViewModel viewModel;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView homeMessage =
                view.findViewById(R.id.homeMessage);

        Button btnNext =
                view.findViewById(R.id.btnNext);

        viewModel = new ViewModelProvider(
                requireActivity()
        ).get(MainViewModel.class);

        viewModel.getMessage().observe(
                getViewLifecycleOwner(),
                message -> homeMessage.setText(message)
        );

        btnNext.setOnClickListener(v ->
                Navigation.findNavController(v)
                        .navigate(R.id.action_home_to_second)
        );
    }
}
