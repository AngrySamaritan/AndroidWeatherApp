package com.ilearning.tasks.ilearningweatherapp.util;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyHttpRequest extends AsyncTask<Request, Boolean, JSONObject> {

    final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Request... requests) {
        JSONObject responseJson = null;

        Response response = null;
        try {
            response = client.newCall(requests[0]).execute();
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: logs
        }

        String responseBodyString = "";


        try {
            responseBodyString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            responseJson = new JSONObject(responseBodyString);
        } catch (JSONException e) {
            try {
                responseJson = new JSONArray(responseBodyString).optJSONObject(0);
            } catch (JSONException ex) {
                ex.printStackTrace();
                // TODO: logs
            }
        } catch (NullPointerException e){
            e.printStackTrace();
            // TODO: logs
        }

        return responseJson;
    }


    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }

    public HttpUrl getRequestUrl(String url, Map<String, String> attributes) {

        final HttpUrl.Builder httpBuilder = Objects.requireNonNull(
                HttpUrl.parse(url)).newBuilder();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            httpBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }

        return httpBuilder.build();
    }

}
