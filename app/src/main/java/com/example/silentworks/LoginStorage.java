package com.example.silentworks;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginStorage {

    private final String usernameKey = "username";
    private final String passwordKey = "password";
    private SharedPreferences sharedPreferences;

    public LoginStorage (String username, String password, Context context) throws Exception {

        sharedPreferences = context.getSharedPreferences("SilentWorksLogin", Context.MODE_PRIVATE);
        if (sharedPreferences.getString(usernameKey, "").equals("") &&
                sharedPreferences.getString(passwordKey, "").equals("")) {
            if (username != null && password != null) {
                sharedPreferences.edit().putString(usernameKey, username).apply();
                sharedPreferences.edit().putString(passwordKey, password).apply();
            } else {
                throw new Exception("Error");
            }
        }

    }

    public String getUsername() {
        return sharedPreferences.getString(usernameKey,"");
    }

    public String getPasswordKey() {
        return sharedPreferences.getString(passwordKey,"");
    }

    public void setUsername(String username) {
        sharedPreferences.edit().putString(usernameKey, username).apply();
    }

    public void setPassword(String password) {
        sharedPreferences.edit().putString(passwordKey, password).apply();
    }

    public void delete() {
        sharedPreferences.edit().remove(usernameKey).apply();
        sharedPreferences.edit().remove(passwordKey).apply();
    }
}
