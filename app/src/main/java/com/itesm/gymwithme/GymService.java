package com.itesm.gymwithme;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GymService {

    @POST("login")
    Call<User> logIn(@Body User user);

    @POST("register")
    Call<User> register(@Body User user);

}
