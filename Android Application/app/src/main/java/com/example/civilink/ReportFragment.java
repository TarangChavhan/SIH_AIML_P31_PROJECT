package com.example.civilink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ReportFragment extends Fragment {

    private EditText etTitle, etDescription, etLocation, etAdditionalDetails;
    private Button btnAddPhoto, btnAddVideo, btnSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report, container, false);

        // Initialize views
        etTitle = view.findViewById(R.id.et_title);
        etDescription = view.findViewById(R.id.et_description);
        etLocation = view.findViewById(R.id.et_location);
        etAdditionalDetails = view.findViewById(R.id.et_additional_details);
        btnAddPhoto = view.findViewById(R.id.btn_add_photo);
        btnAddVideo = view.findViewById(R.id.btn_add_video);
        btnSubmit = view.findViewById(R.id.btn_submit);

        // Button listeners
        btnAddPhoto.setOnClickListener(v ->
                Toast.makeText(getContext(), "Add Photo clicked", Toast.LENGTH_SHORT).show()
        );

        btnAddVideo.setOnClickListener(v ->
                Toast.makeText(getContext(), "Add Video clicked", Toast.LENGTH_SHORT).show()
        );

        btnSubmit.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String desc = etDescription.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String details = etAdditionalDetails.getText().toString().trim();

            if (title.isEmpty() || desc.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in Title and Description", Toast.LENGTH_SHORT).show();
            } else {
                // For now, just show a Toast (later you can connect with backend / database)
                Toast.makeText(getContext(),
                        "Report Submitted\nTitle: " + title,
                        Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}

