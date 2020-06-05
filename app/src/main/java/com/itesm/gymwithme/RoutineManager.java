package com.itesm.gymwithme;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class RoutineManager {
    private static GymService gymService = ServiceBuilder.INSTANCE.buildService(GymService.class);

    public static void getAllRoutines(Callback<List<Routine>> callback) {
        Call<List<Routine>> listCall = gymService.getAllRoutines();
        listCall.enqueue(callback);

    }

}
