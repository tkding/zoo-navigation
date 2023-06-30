package com.example.sdzooseeker_team_64;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class MyPrefs {
    public static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences sp = context.getSharedPreferences("public", Context.MODE_PRIVATE);
        return sp;
    }
    public static void setLastActivity(Context context, String key, String value) {
        SharedPreferences myPref = getSharedPreferences(context);
        SharedPreferences.Editor editor = myPref.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getLastActivity(Context context, String key) {
        SharedPreferences myPref = getSharedPreferences(context);
        return myPref.getString(key, "MainActivity");
    }

    public static void saveString(Context context, String name, String str, int index) {
        SharedPreferences myPref = getSharedPreferences(context);
        SharedPreferences.Editor editor = myPref.edit();
        editor.putString(name+index, str);
        editor.apply();
    }
    public static ZooGraph.Exhibit getTheExhibit(Context context, String key, ZooGraph zg) {
        SharedPreferences myPref = getSharedPreferences(context);
        String id = myPref.getString(key, "");
        return zg.getExhibitWithId(id);
    }
    public static String getTheString(Context context, String key) {
        SharedPreferences myPref = getSharedPreferences(context);
        return myPref.getString(key, "");
    }
    public static void saveLength(Context context, String name, int length) {
        SharedPreferences myPref = getSharedPreferences(context);
        SharedPreferences.Editor editor = myPref.edit();
        editor.putInt(name, length);
        editor.apply();
    }
    public static int getTheLength(Context context, String key) {
        SharedPreferences myPref = getSharedPreferences(context);
        return myPref.getInt(key, 0);
    }

    public static int getLengthDefaultOne(Context context, String key) {
        SharedPreferences myPref = getSharedPreferences(context);
        return myPref.getInt(key, 1);
    }

    public static void delete(Context context, String key) {
        SharedPreferences myPref = getSharedPreferences(context);
        SharedPreferences.Editor editor = myPref.edit();
        editor.remove(key);
    }

}