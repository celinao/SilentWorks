package com.example.silentworks;

import android.os.Bundle;
import android.view.View;

import java.io.Serializable;

public class MainPage extends OptionsMenu implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toGoogleLogin(View view) {

    }
}

