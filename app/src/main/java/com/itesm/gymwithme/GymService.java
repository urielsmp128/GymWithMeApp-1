package com.itesm.gymwithme;

import com.itesm.gymwithme.LoginUser;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GymService {

    @POST("login")
    Call<LoginUser> logIn(@Body LoginUser loginUser);

}
