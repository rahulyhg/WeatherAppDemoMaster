package com.weatherappdemomaster;

import android.content.Context;
import android.support.annotation.Nullable;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RemoteFetch {

    @Nullable
    public static JSONObject getJSON(Context context, String city) {
        try {
            URL url = new URL(String.format(Common.OPEN_WEATHER_MAP_API, city));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("x-api-key", context.getString(R.string.open_weather_maps_app_id));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuilder json = new StringBuilder(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
            if (data.getInt("cod") != 200) {
                return null;
            }

            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}