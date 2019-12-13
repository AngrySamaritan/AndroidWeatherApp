package com.ilearning.tasks.ilearningweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ilearning.tasks.ilearningweatherapp.fragments.HistoryFragment;
import com.ilearning.tasks.ilearningweatherapp.model.MarkerInfo;
import com.ilearning.tasks.ilearningweatherapp.model.Weather;
import com.ilearning.tasks.ilearningweatherapp.util.MarkersMaker;
import com.ilearning.tasks.ilearningweatherapp.util.WeatherRequester;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private MarkerInfo[] markers;

    public final int MARKERS_AMOUNT = 5;

    private Toolbar toolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_history:
                    loadFragment(HistoryFragment.newInstance());
                    return true;
                case R.id.navigation_map:
                    replaceMap();
                    return true;
            }
            return false;
        }
    };

    private SupportMapFragment mapFragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh) {
            markers = MarkersMaker.getMarkers(MARKERS_AMOUNT);
            replaceMap();
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        markers = MarkersMaker.getMarkers(MARKERS_AMOUNT);

        setContentView(R.layout.activity_maps);

        toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mainFragment);

         mapFragment.getMapAsync(this);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFragment, fragment)
                .commit();
    }

    private void replaceMap() {
        loadFragment(mapFragment);
        mMap.clear();
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = MarkersMaker.addMarkers(googleMap, markers);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                String locationKey = marker.getTitle();
                WeatherRequester weatherRequester = new WeatherRequester();
                Weather[] weather = new Weather[0];
                try {
                    weather = weatherRequester.getWeather(locationKey);
                } catch (InterruptedException | ExecutionException |
                        TimeoutException | JSONException e) {
                    e.printStackTrace();
                }

                Intent weatherIntent = new Intent(MapActivity.this,
                        WeatherActivity.class);



                weatherIntent.putExtra("forecast", weather);
                weatherIntent.putExtra("position", marker.getPosition());

                startActivity(weatherIntent);
                return false;
            }
        });
    }






}
