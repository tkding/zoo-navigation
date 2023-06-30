package com.example.sdzooseeker_team_64;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public class Dispatcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Class<?> activityClass;

        try {
            String name = MyPrefs.getLastActivity(App.getContext(),"lastActivity");
            activityClass = Class.forName(name);
        } catch (ClassNotFoundException e) {
            activityClass = MainActivity.class;
        }
        if(activityClass != MainActivity.class) {
            startActivity(new Intent(this, MainActivity.class));
        }
        startActivity(new Intent(this, activityClass));
    }
}
