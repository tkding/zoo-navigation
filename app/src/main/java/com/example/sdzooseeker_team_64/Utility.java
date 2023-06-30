package com.example.sdzooseeker_team_64;

import android.app.AlertDialog;
import android.content.Context;

public class Utility {

    //alert
    public static void popAlert(Context context, String msg){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder
                .setTitle("Alert:")
                .setMessage(msg)
                .setPositiveButton("Yes", (dialog,id)->{
                    dialog.cancel();
                })
                .setNegativeButton("No", (dialog,id)->{
                    dialog.cancel();
                })
                .setCancelable(true);
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

}
