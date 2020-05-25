package com.itesm.gymwithme.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.itesm.gymwithme.CameraXActivity;
import com.itesm.gymwithme.R;

public class QRScanWorkoutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_qr_scan_workout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = getActivity().findViewById(R.id.scan_button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CameraXActivity.class);
            startActivity(intent);
        });
    }
}
