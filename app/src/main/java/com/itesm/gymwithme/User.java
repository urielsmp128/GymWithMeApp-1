package com.itesm.gymwithme;

import android.os.Bundle;

public class User {

    private String username;
    private String password;
    private String token;

    private String name;
    private Integer age;
    private String gender;
    private String beenWorkingOutFor;

    public User(String userId, String password) {
        this.username = userId;
        this.password = password;
    }

    public User(String username, String password, String name, Integer age, String gender, String beenWorkingOutFor) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.beenWorkingOutFor = beenWorkingOutFor;
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
