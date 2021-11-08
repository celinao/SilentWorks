package com.example.silentworks;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class OptionsMenu extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.calendar:
                Log.i("tag", "clicked calendar");
                startActivity(new Intent(this, CalendarActivity.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(this, settings.class));
                return true;
            case R.id.help:
                startActivity(new Intent(this, HelpPage.class));
                return true;
            case R.id.notifications:
                startActivity(new Intent(this, NotificationPage.class));
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }
}
