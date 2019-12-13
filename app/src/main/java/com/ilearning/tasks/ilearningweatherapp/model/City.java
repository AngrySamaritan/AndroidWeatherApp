package com.ilearning.tasks.ilearningweatherapp.model;

import com.google.android.gms.maps.model.LatLng;

public class City {

    private String name;
    private LatLng latLng;
    private String locationKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public City(String locationKey, String name, LatLng latLng) {
        this.locationKey = locationKey;
        this.name = name;
        this.latLng = latLng;
    }

    public String getLocationKey() {
        return locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }
}
