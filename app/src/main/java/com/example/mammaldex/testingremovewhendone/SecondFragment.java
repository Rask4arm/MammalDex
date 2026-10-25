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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mammaldex.R;

public class SecondFragment extends Fragment {

    private MainViewModel viewModel;
    private TestSpeciesViewAdapter adapter;

    public SecondFragment() {
        super(R.layout.fragment_second);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView secondMessage =
                view.findViewById(R.id.secondMessage);

        RecyclerView recyclerView =
                view.findViewById(R.id.species_list);

        Button btnHome =
                view.findViewById(R.id.btnHome);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        adapter = new TestSpeciesViewAdapter();

        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(
                requireActivity()
        ).get(MainViewModel.class);

        viewModel.getMessage().observe(
                getViewLifecycleOwner(),
                message -> secondMessage.setText(message)
        );

        // Demonstrate that both Fragments share the ViewModel.
        viewModel.updateMessage("You are on the Second Screen!");

        btnHome.setOnClickListener(v ->
                Navigation.findNavController(v)
                        .popBackStack()
        );
    }
}
