package com.example.sdzooseeker_team_64;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

public class LatLngs {
    public static final LatLng UCSD_LATLNG = new LatLng(32.8801, -117.2340);
    public static final LatLng ZOO_LATLNG = new LatLng(32.7353, -117.1490);

    @NonNull
    static LatLng midpoint(LatLng l1, LatLng l2) {
        return new LatLng(
                (l1.latitude + l2.latitude) / 2,
                (l1.longitude + l2.longitude) / 2
        );
    }
}
