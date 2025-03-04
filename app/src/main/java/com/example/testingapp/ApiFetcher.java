package com.example.testingapp;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiFetcher {
    private static final String API_URL = "https://bufferedky.com.ng/movies/api/movie";
    private final OkHttpClient client = new OkHttpClient();

    public void fetchRecords() {
        Request request = new Request.Builder()
                .url(API_URL)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() { // Async call
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace(); // Handle error
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    parseJson(responseData);
                }
            }
        });
    }

    private void parseJson(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String name = jsonObject.getString("name");
//                int id = jsonObject.getInt("id");

                System.out.println(jsonObject.toString());
//                Toast.makeText(home.this, jsonObject.toString(), Toast.LENGTH_LONG);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
