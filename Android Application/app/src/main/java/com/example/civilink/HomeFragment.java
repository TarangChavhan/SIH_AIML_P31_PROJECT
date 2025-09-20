package com.example.civilink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Example: set click listeners on cards
        view.findViewById(R.id.grid_home);

        view.findViewById(R.id.grid_home).setOnClickListener(v ->
                Toast.makeText(getContext(), "Clicked Home Grid", Toast.LENGTH_SHORT).show()
        );

        return view;
    }
}