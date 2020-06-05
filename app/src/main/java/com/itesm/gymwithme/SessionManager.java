package com.itesm.gymwithme;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("AppKey", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void setLogin(boolean login) {
        editor.putBoolean("KEY_LOGIN", login);
        editor.commit();
    }

    public boolean getLogin() {
        return sharedPreferences.getBoolean("KEY_LOGIN", false);
    }

    public void setToken(String token) {
        editor.putString("KEY_TOKEN", token);
        editor.commit();
    }

    public String getToken() {
        return sharedPreferences.getString("KEY_TOKEN", "");
    }

    public void setName(String user) {
        editor.putString("KEY_NAME", user);
        editor.commit();
    }

    public String getName() {
        return sharedPreferences.getString("KEY_NAME", "User");
    }
}
