package com.itesm.gymwithme.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.gymwithme.R;

public class RoutineDetailFragment extends Fragment {

    private TextView textTitle;
    private TextView textMuscles;
    private TextView textLevel;
    private TextView textAuthor;
    private TextView textWorkout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_routine_detail, container, false);

        Bundle args = getArguments();

        textTitle = view.findViewById(R.id.title_text);
        textMuscles = view.findViewById(R.id.muscle_text);
        textLevel = view.findViewById(R.id.difficulty_text);
        textAuthor = view.findViewById(R.id.author_text);
        textWorkout = view.findViewById(R.id.info_text);

        textTitle.setText(args.getCharSequence("title"));
        textMuscles.setText(args.getCharSequence("muscles"));
        textLevel.setText(args.getCharSequence("level"));
        textAuthor.setText(args.getCharSequence("author"));
        textWorkout.setText(args.getCharSequence("workout"));



        return view;
    }
}
