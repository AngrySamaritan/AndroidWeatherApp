package com.ilearning.tasks.ilearningweatherapp.model;

import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Weather implements Serializable {

    private double minTemperature;
    private double maxTemperature;
    private String day;
    private String night;
    private String date;

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @NotNull
    public String toString(){
        return "Day: " + day + "Maximum Temperature: " + maxTemperature +
        "\n Night: " + night + "Minimum Temperature" + minTemperature;
    }
}
