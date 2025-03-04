package com.example.testingapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SyncWorker extends Worker {

    private static final String API_URL = "https://bufferedky.com.ng/movies/api/movie";
    private RequestQueue requestQueue;

    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        requestQueue = Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        fetchData();
        return Result.success();
    }

    private void fetchData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, API_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String title = response.getString("title");
//                            Toast.makeText(MainActivity,Toast.LENGTH_LONG, response.toString());
                            Log.d("SyncWorker", "Fetched Data: " + title);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("SyncWorker", "VolleyError: " + error.toString());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}
