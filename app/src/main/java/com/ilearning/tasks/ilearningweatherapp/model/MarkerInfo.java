package com.ilearning.tasks.ilearningweatherapp.model;

import com.google.android.gms.maps.model.LatLng;

public class MarkerInfo {

    LatLng coords;
    String locationKey;


    public LatLng getCoords() {
        return coords;
    }

    public void setCoords(LatLng coords) {
        this.coords = coords;
    }

    public String getLocationKey() {
        return locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }


    public MarkerInfo(LatLng coords, String title) {
        this.coords = coords;
        this.locationKey = title;
    }
}
