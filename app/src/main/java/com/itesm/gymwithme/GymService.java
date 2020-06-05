package com.itesm.gymwithme;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GymService {

    @POST("user/login")
    Call<User> logIn(@Body User user);

    @POST("user/register")
    Call<User> register(@Body User user);

    @GET("routines")
    Call<List<Routine>> getAllRoutines();


}
