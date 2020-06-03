package com.itesm.gymwithme;


import retrofit2.Call;
import retrofit2.Callback;

public class UserManager {

    private static GymService gymService = ServiceBuilder.INSTANCE.buildService(GymService.class);

    public static void loginUser(User user, Callback<User> callback) {
        Call<User> userCall = gymService.logIn(user);
        userCall.enqueue(callback);
    }

    public static void register(User user, Callback<User> callback) {


        Call<User> userCall = gymService.register(user);
        userCall.enqueue(callback);

    }
}
