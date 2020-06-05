package com.itesm.gymwithme;

import android.os.Bundle;

import com.google.gson.annotations.SerializedName;

public class User {

    private String username;
    private String password;
    private String token;

    @SerializedName("_id")
    private String id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBeenWorkingOutFor() {
        return beenWorkingOutFor;
    }

    public void setBeenWorkingOutFor(String beenWorkingOutFor) {
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

    public String firstName() {
        String str = "";
        for (int i = 0; i < this.name.length(); i++) {
            if (this.name.charAt(i) == ' ') {
                break;
            }
            str += this.name.charAt(i);
        }
        return str;
    }
}
