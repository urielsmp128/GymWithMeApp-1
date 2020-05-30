package com.itesm.gymwithme;

import android.os.Bundle;

public class LoginUser {

    private String username;
    private String password;
    private String token;

    public LoginUser(String userId, String password) {
        this.username = userId;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static String getToken(Bundle bundle) {
        try {
            return bundle.getCharSequence("auth-token").toString();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        }
    }
}
