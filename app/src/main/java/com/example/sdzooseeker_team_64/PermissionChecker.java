package com.example.sdzooseeker_team_64;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import java.util.Arrays;

public class PermissionChecker {
    private ComponentActivity activity;

    final ActivityResultLauncher<String[]> requestPermissionLauncher;

    public PermissionChecker(ComponentActivity activity) {
        this.activity = activity;
        requestPermissionLauncher = activity.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), perms -> {
            perms.forEach((perm, isGranted) -> {
                Log.i("ZOO LOCATION", String.format("Permission %s granted: %s", perm, isGranted));
            });
        });
    }

    boolean ensurePermissions() {
        var requiredPermissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        var hasNoLocationsPerms = Arrays.stream(requiredPermissions)
                .map(perm -> ContextCompat.checkSelfPermission(activity, perm))
                .allMatch(status -> status == PackageManager.PERMISSION_DENIED);

        if (hasNoLocationsPerms) {
            requestPermissionLauncher.launch(requiredPermissions);
            // Note: the activity will be restarted when permission change!
            // This entire method wil be re-run, but we won't get stuck here.
            return true;
        }
        return false;
    }
}