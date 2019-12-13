package com.ilearning.tasks.ilearningweatherapp.util;

import com.google.android.gms.maps.model.LatLng;
import com.ilearning.tasks.ilearningweatherapp.model.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Request;

public class CitiesRequester {


    private static final String MAX_COUNT = "1000";
    private final String VK_API_SERVICE_KEY =
            "b2075fbeb2075fbeb2075fbe8fb269d24cbb207b2075fbeeff5c0b2148ae42171c28cc3";
    private final String VK_API_URL = "https://api.vk.com/method/";
    private final String VK_API_VERSION = "5.103";
    private final String VK_API_LANG = "en";
    private final String ACUWEATHER_API_KEY = "svEvMD6gKTFj6UwAOPfPD7nYiZybayZV";
    private final String ACUWEATHER_API_URL =
            "http://dataservice.accuweather.com/";


    public City[] getRandomCities(int amount) throws JSONException, InterruptedException,
            ExecutionException, TimeoutException {
        String[] cityNames;
        cityNames = getRandomCitiesNames(amount);

        City[] cities = new City[amount];

        for (int i = 0; i < amount; i++) {
            cities[i] = getCityByName(cityNames[i]);
        }

        return cities;
    }


    private City getCityByName(String name) throws InterruptedException, ExecutionException,
            TimeoutException {
        final MyHttpRequest httpRequester = new MyHttpRequest();
        String GET_CITY_METHOD = "locations/v1/cities/search";
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("apikey", ACUWEATHER_API_KEY);
        requestMap.put("q", name);

        boolean isRussian = hasCyrilic(name);

        if (isRussian) {
            requestMap.put("language", "ru-ru");
        }

        Request request = new Request.Builder()
                .url(httpRequester.getRequestUrl(
                        ACUWEATHER_API_URL + GET_CITY_METHOD, requestMap))
                .build();


        // TODO: DEBUG
        isIncorrect(name);



        JSONObject response = httpRequester.execute(request).get(5, TimeUnit.SECONDS);


        City city = null;

        try {
            String locationKey = response.getString("Key");
            String englishName = response.getString("EnglishName");
            double latitude = response.getJSONObject("GeoPosition").getDouble("Latitude");
            double longitude = response.getJSONObject("GeoPosition").getDouble("Longitude");
            city = new City(locationKey, englishName, new LatLng(latitude, longitude));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return city;
    }


    private String[] getRandomCitiesNames(int amount) throws JSONException, InterruptedException,
            ExecutionException, TimeoutException {
        String[] countries = getRandomCountriesIds(amount);
        String[] cityNames = new String[amount];
        Stack<String> testStack = new Stack<>();
        for (String id: countries){
            testStack.push(id);
        }

        for (int i = 0; i < amount; i++) {
            try {
                cityNames[i] = getRandomCityName(countries[i]);
            } catch (JSONException e){

                int oldSize = testStack.size();

                String randomCountry = getRandomCountriesIds(1)[0];
                testStack.push(randomCountry);

                while (oldSize == testStack.size()) {
                    randomCountry = getRandomCountriesIds(1)[0];
                    testStack.push(randomCountry);
                }

                countries[i] = randomCountry;
                i--;

            }
            cityNames[i] = toNormalEncode(cityNames[i]);
        }

        return cityNames;
    }

    private String getRandomCityName(String countryId) throws ExecutionException,
            InterruptedException, TimeoutException, JSONException {
        final MyHttpRequest httpRequester = new MyHttpRequest();
        String GET_CITIES_METHOD = "database.getCities";
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("access_token", VK_API_SERVICE_KEY);
        requestMap.put("v", VK_API_VERSION);
        requestMap.put("need_all", "0");
        requestMap.put("lang", VK_API_LANG);
        requestMap.put("country_id", countryId);

        Request request = new Request.Builder()
                .url(httpRequester.getRequestUrl(VK_API_URL + GET_CITIES_METHOD, requestMap))
                .build();

        JSONObject response = httpRequester.execute(request).get(5, TimeUnit.SECONDS);

        String cityName;
        cityName = response
                .getJSONObject("response")
                .getJSONArray("items")
                .getJSONObject(0)
                .getString("title");

        return cityName;
    }

    private String[] getRandomCountriesIds(int amount) throws InterruptedException,
            ExecutionException, TimeoutException, JSONException {
        JSONObject response = getAllCountriesIds();

        String[] countries = new String[amount];

        int arraySize = response.getInt("count");
        JSONArray array = response.getJSONArray("items");

        Random random = new Random();
        Stack<Integer> testStack = new Stack<>();

        for (int i = 0; i < amount; i++) {
            int oldSize = testStack.size();
            int randomIndex = 0;
            while (oldSize == testStack.size()) {
                randomIndex = random.nextInt(arraySize);
                testStack.push(randomIndex);
            }
            countries[i] = array.getJSONObject(randomIndex).getString("id");
        }
        return countries;
    }

    private JSONObject getAllCountriesIds() throws JSONException, InterruptedException,
            ExecutionException, TimeoutException {
        final MyHttpRequest httpRequester = new MyHttpRequest();

        JSONObject response;

        String GET_COUNTRIES_METHOD = "database.getCountries";
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("access_token", VK_API_SERVICE_KEY);
        requestMap.put("v", VK_API_VERSION);
        requestMap.put("need_all", "1");
        requestMap.put("lang", VK_API_LANG);
        requestMap.put("count", MAX_COUNT);


        Request request = new Request.Builder()
                .url(httpRequester.getRequestUrl(VK_API_URL + GET_COUNTRIES_METHOD, requestMap))
                .build();

        response = httpRequester.execute(request)
                .get(5, TimeUnit.SECONDS)
                .getJSONObject("response");

        return response;
    }

    private String toNormalEncode(String name) {
        Map<Character, Character> fixMap = new HashMap<>();
        fixMap.put('ā', 'a');
        fixMap.put('ū', 'u');
//        fixMap.put('Ā', 'A');
        fixMap.put('ī', 'i');
        fixMap.put('é', 'e');
        fixMap.put('ḩ', 'h');
        fixMap.put('ă', 'a');
        fixMap.put('ö', 'o');
        fixMap.put('â', 'i');
        fixMap.put('ë', 'e');
        fixMap.put('á', 'a');
        fixMap.put('ó', 'o');
        fixMap.put('ł', 'l');
        fixMap.put('ş', 's');




        StringBuilder fixedName = new StringBuilder(name.toLowerCase());
        for (int i = 0; i < fixedName.length(); i++) {
            if (fixMap.get(fixedName.charAt(i)) != null) {
                fixedName.setCharAt(i, fixMap.get(fixedName.charAt(i)));
            }
            if (fixedName.charAt(i) == '/'){
                fixedName.delete(i, fixedName.length());
                break;
            }
        }
        fixedName.setCharAt(0, Character.toUpperCase(fixedName.charAt(0)));
        return fixedName.toString();
    }

    private boolean hasCyrilic(String name) {
        boolean russian = false;
        for (Character symbol : name.toLowerCase().toCharArray()) {
            if (symbol <= 'я' && symbol >= 'а') {
                russian = true;
                break;
            }
        }
        return russian;
    }

    // TODO: DEBUG
    private void isIncorrect(String name) {
        String allowedSymbols =
                "qwertyuioppasdfghjklzxcvbnmnйцукенгшщзхъфывапролджэячсмитьббю`1234567890-. ";
        for (Character symbol : name.toLowerCase().toCharArray()) {
            if (allowedSymbols.indexOf(symbol) == -1) {
                System.out.println(symbol);
                System.out.println(name);
                break;
            }
        }
    }
}
