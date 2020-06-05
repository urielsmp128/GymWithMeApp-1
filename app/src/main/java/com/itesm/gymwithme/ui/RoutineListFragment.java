package com.itesm.gymwithme.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.itesm.gymwithme.R;
import com.itesm.gymwithme.Routine;
import com.itesm.gymwithme.RoutineAdapter;
import com.itesm.gymwithme.RoutineManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoutineListFragment extends Fragment implements Callback<List<Routine>> {


    private ArrayList<Routine> routines;
    private RecyclerView recyclerView;
    private RoutineAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_routine_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        RoutineManager.getAllRoutines(this);

        return view;
    }

    @Override
    public void onResponse(Call<List<Routine>> call, Response<List<Routine>> response) {
        if (!response.isSuccessful()) {
            Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
            return;
        }

        routines = (ArrayList<Routine>) response.body();
        adapter = new RoutineAdapter(getContext(), routines);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onFailure(Call<List<Routine>> call, Throwable t) {
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
