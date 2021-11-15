package com.example.silentworks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;

public class MainPage extends OptionsMenu implements Serializable {
    // remove Options Menu and replace with AppCompat once the log-in features work since there
    // shouldn't be a menu on this page.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(R.layout.activity_calendar);
//        setContentView(R.layout.activity_settings);
    }

    public void toGoogleLogin(View view) {

    }
}

