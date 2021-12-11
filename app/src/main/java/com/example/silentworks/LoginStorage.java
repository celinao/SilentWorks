package com.example.silentworks;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class handles sharedPreference storage. The only thing is stores is user information. Used mainly
 * in off-line mode.
 */
public class LoginStorage {

    private final String usernameKey = "username";
    private final String passwordKey = "password";
    private SharedPreferences sharedPreferences;

    public LoginStorage (Context context){

        sharedPreferences = context.getSharedPreferences("SilentWorksLogin", Context.MODE_PRIVATE);

    }

    public String getUsername() {
        return sharedPreferences.getString(usernameKey,"");
    }

    public String getPassword() {
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
