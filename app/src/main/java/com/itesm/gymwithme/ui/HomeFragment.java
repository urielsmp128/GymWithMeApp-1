package com.itesm.gymwithme.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.card.MaterialCardView;
import com.itesm.gymwithme.R;

public class HomeFragment extends Fragment {

    private MaterialCardView cardViewWorkout;
    private MaterialCardView cardViewScan;
    private MaterialCardView cardViewConnect;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cardViewWorkout = view.findViewById(R.id.card_workout);
        cardViewScan = view.findViewById(R.id.card_scan);
        cardViewConnect = view.findViewById(R.id.card_connect);

        cardViewWorkout.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_routineListFragment));

        cardViewScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_cameraXActivity);
            }
        });

        return view;
    }
}
