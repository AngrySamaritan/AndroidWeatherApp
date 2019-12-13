package com.ilearning.tasks.ilearningweatherapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ilearning.tasks.ilearningweatherapp.R;

public class FiveDaysWeatherFragment extends Fragment {
    public FiveDaysWeatherFragment(){
    }

    public static FiveDaysWeatherFragment newInstance(){
        return new FiveDaysWeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.five_day_weather_fragment, container, false);
    }
}
