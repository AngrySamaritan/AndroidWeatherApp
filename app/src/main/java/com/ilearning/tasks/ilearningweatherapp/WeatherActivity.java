package com.ilearning.tasks.ilearningweatherapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ilearning.tasks.ilearningweatherapp.fragments.FiveDaysWeatherFragment;
import com.ilearning.tasks.ilearningweatherapp.fragments.OneDayWeatherFragment;
import com.ilearning.tasks.ilearningweatherapp.model.Weather;

import java.util.concurrent.TimeUnit;


public class WeatherActivity extends FragmentActivity {

    private Bundle arguments;

    private OneDayWeatherFragment oneDayWeatherFragment = OneDayWeatherFragment.newInstance();
    private FiveDaysWeatherFragment fiveDaysWeatherFragment = FiveDaysWeatherFragment.newInstance();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.weather_one_day:
                    loadFragment(oneDayWeatherFragment);
                    loadOneDay();
                    System.out.println("DEBUG: one day");
                    return true;
                case R.id.weather_five_day:
                    loadFragment(fiveDaysWeatherFragment);
                    loadFiveDay();
                    System.out.println("DEBUG: five days");
                    return true;
            }
            return false;
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.weather_activity);
        arguments = getIntent().getExtras();
        loadOneDay();

        getSupportFragmentManager().beginTransaction().add(
                R.id.fragment_weather, fiveDaysWeatherFragment);

        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BottomNavigationView navigation = findViewById(R.id.weather_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_weather, fragment)
                .commit();
    }

    private void loadOneDay() {

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Weather[] weather = (Weather[]) arguments.get("forecast");
        LatLng position = (LatLng) arguments.get("position");


        TextView field = findViewById(R.id.text_date);
        field.setText(weather[0].getDate());

        field = findViewById(R.id.latitude);
        field.setText(String.valueOf(position.latitude));

        field = findViewById(R.id.longitude);
        field.setText(String.valueOf(position.longitude));

        field = findViewById(R.id.dayWeather);
        field.setText(String.valueOf(weather[0].getDay()));

        field = findViewById(R.id.nightWeather);
        field.setText(String.valueOf(weather[0].getNight()));

        field = findViewById(R.id.minTemp);
        field.setText(String.valueOf(weather[0].getMinTemperature()));

        field = findViewById(R.id.maxTemp);
        field.setText(String.valueOf(weather[0].getMaxTemperature()));
    }

    private void loadFiveDay() {

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Weather[] weather = (Weather[]) arguments.get("forecast");
//
//        TextView field = findViewById(R.id.day1);
//        field.setText(weather[0].toString());
//
//        field = findViewById(R.id.day2);
//        field.setText(weather[1].toString());
//
//        field = findViewById(R.id.day3);
//        field.setText(weather[2].toString());
//
//        field = findViewById(R.id.day4);
//        field.setText(weather[3].toString());
//
//        field = findViewById(R.id.day5);
//        field.setText(weather[5].toString());
    }
}
