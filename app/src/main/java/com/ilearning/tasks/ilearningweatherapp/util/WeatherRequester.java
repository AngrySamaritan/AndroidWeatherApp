package com.ilearning.tasks.ilearningweatherapp.util;

import com.ilearning.tasks.ilearningweatherapp.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class WeatherRequester {
    private final OkHttpClient client = new OkHttpClient();

    public Weather[] getWeather(String locationKey) throws InterruptedException, ExecutionException,
            TimeoutException, JSONException {
        MyHttpRequest httpRequester = new MyHttpRequest();

        Map<String, String> requestMap = new HashMap<>();

        // TODO: Refactor this fucking hardcode :-)

        String ACUWEATHER_API_KEY = "svEvMD6gKTFj6UwAOPfPD7nYiZybayZV";
        requestMap.put("apikey", ACUWEATHER_API_KEY);

        requestMap.put("metric", "true");

        String ACUWEATHER_GETWEATHER5_API =
                "http://dataservice.accuweather.com/forecasts/v1/daily/5day/";

        Request request = new Request.Builder()
                .url(httpRequester.getRequestUrl(
                        ACUWEATHER_GETWEATHER5_API + locationKey, requestMap))
                .build();

        JSONObject response = httpRequester.execute(request).get(5, TimeUnit.SECONDS);

        JSONArray forecastArray = response.getJSONArray("DailyForecasts");
        Weather[] weather = new Weather[forecastArray.length()];

        for (int i = 0; i < weather.length; i++){
            weather[i] = new Weather();
            weather[i].setDay(forecastArray.
                    getJSONObject(i)
                    .getJSONObject("Day")
                    .getString("IconPhrase"));
            weather[i].setNight(forecastArray.
                    getJSONObject(i)
                    .getJSONObject("Night")
                    .getString("IconPhrase"));
            weather[i].setMinTemperature(forecastArray.
                    getJSONObject(i)
                    .getJSONObject("Temperature")
                    .getJSONObject("Minimum")
                    .getDouble("Value"));
            weather[i].setMaxTemperature(forecastArray.
                    getJSONObject(i)
                    .getJSONObject("Temperature")
                    .getJSONObject("Maximum")
                    .getDouble("Value"));
            weather[i].setDate(forecastArray.
                    getJSONObject(i)
                    .getString("Date"));
        }

        return weather;
    }

}


