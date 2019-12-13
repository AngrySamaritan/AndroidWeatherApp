package com.ilearning.tasks.ilearningweatherapp.util;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ilearning.tasks.ilearningweatherapp.model.City;
import com.ilearning.tasks.ilearningweatherapp.model.MarkerInfo;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class MarkersMaker {

    public static MarkerInfo[] getMarkers(int amount) {
        CitiesRequester citiesRequester = new CitiesRequester();
        MarkerInfo[] markers = new MarkerInfo[amount];
        City[] cities = new City[amount];
        try {
            cities = citiesRequester.getRandomCities(amount);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < cities.length; i++){
            markers[i] = new MarkerInfo(cities[i].getLatLng(), cities[i].getLocationKey());
        }

        return markers;
    }

    public static GoogleMap addMarkers(GoogleMap mMap, MarkerInfo[] markers) {
        for (int i = 0; i < markers.length; i++) {
            mMap.addMarker(new MarkerOptions()
                    .position(markers[i].getCoords())
                    .title(markers[i].getLocationKey()));
        }
        return mMap;
    }

}
