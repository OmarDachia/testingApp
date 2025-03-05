package com.example.testingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import androidx.viewpager.widget.ViewPager;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class home extends AppCompatActivity implements MovieItemClickListener,SliderPagerAdapter.OnSlideClickListener {
    private List<Slide> lstSlides ;
    private ViewPager sliderpager;
    private TabLayout indicator;
    private RecyclerView MoviesRV, MoviesRVHausa ;
    private static final String API_URL = "https://bufferedky.com.ng/movies/api/movie";
    private Handler handler = new Handler();
    private static final int INTERVAL = 5000; // 5 seconds
    private RequestQueue requestQueue;
    List<Movie> lstMovies;
    List<MovieCat> lstMovieCategory;
    private MovieCategoryAdapter MCAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sliderpager = findViewById(R.id.slider_pager) ;
        indicator = findViewById(R.id.indicator);
        MoviesRV = findViewById(R.id.Rv_movies);
        MoviesRVHausa = findViewById(R.id.Rv_moviesHausa);



        requestQueue = Volley.newRequestQueue(this);

//
        lstMovieCategory = new ArrayList<>();
        lstMovieCategory.add(new MovieCat("India Hausa",R.drawable.india_hausa));
        lstMovieCategory.add(new MovieCat("Hausa Films",R.drawable.hausa));
        lstMovieCategory.add(new MovieCat("Tsohuwar Ajiya",R.drawable.tsohuwar_ajiya));
        lstMovieCategory.add(new MovieCat("Waƙoƙin Hausa",R.drawable.wakoki));

        MCAdapter = new MovieCategoryAdapter(this,lstMovieCategory);
        MoviesRVHausa.setAdapter(MCAdapter);
        MoviesRVHausa.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        // prepare a list of slides ..
        lstSlides = new ArrayList<>() ;

        // setup timer
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new home.SliderTimer(),4000,6000);
        indicator.setupWithViewPager(sliderpager,true);

        lstMovies = new ArrayList<>();

        startSync();
//        fetchData();

    }

    @Override
    public void onMovieClick(Movie movie, ImageView movieImageView) {
        // here we send movie information to detail activity
        // also we ll create the transition animation between the two activity
        Intent intent = new Intent(this,movie_detail.class);
        // send movie information to deatilActivity
        intent.putExtra("title",movie.getTitle());
        intent.putExtra("imgURL",movie.getThumbnail());
        intent.putExtra("imgCover",movie.getCoverPhoto());
        intent.putExtra("video",movie.getCoverPhoto());
        // lets crezte the animation
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(home.this,
                movieImageView,"sharedName");

        startActivity(intent,options.toBundle());
    }

    @Override
    public void onSlideClick(Slide slide) {
        Toast.makeText(this,"item clicked : " + slide.getTitle(),Toast.LENGTH_LONG).show();
    }

    class SliderTimer extends TimerTask {


        @Override
        public void run() {

            home.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sliderpager.getCurrentItem()<lstSlides.size()-1) {
                        sliderpager.setCurrentItem(sliderpager.getCurrentItem()+1);
                    }
                    else
                        sliderpager.setCurrentItem(0);
                }
            });


        }
    }

    private void startSync() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchData();
                handler.postDelayed(this, INTERVAL); // Repeat after INTERVAL
            }
        }, INTERVAL);
    }

    private void fetchData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, API_URL, null,
                response -> {
                    try {
//                        String data = response.getString("data");
                        JSONArray jsonArray = response.getJSONArray("data"); // If response is an object with "data" array
                        // JSONArray jsonArray = new JSONArray(response.toString()); // If response is a raw array

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject movieObj = jsonArray.getJSONObject(i);

                            int id = movieObj.getInt("id");
                            String title = movieObj.getString("title");
                            String description = movieObj.optString("description", "No description available");
                            int releaseYear = movieObj.getInt("release_year");
                            int rating = movieObj.getInt("rating");
                            int duration = movieObj.getInt("duration");
                            String thumbnail = movieObj.getString("thumbnail");
                            String genreIds = movieObj.getString("genre_ids");

                            // Log or Display the Data
//                            System.out.println("Title: " + title);
//                            Toast.makeText(home.this, "Title: " + title, Toast.LENGTH_LONG).show();
                            String imageUrl = "https://bufferedky.com.ng/movies/storage/" + thumbnail;
                            String video ="https://bufferedky.com.ng/movies/storage/" + movieObj.getString("video");

                            boolean slideExists = false;
                            for (Slide slide : lstSlides) {
                                if (slide.getImage().equals(imageUrl) && slide.getTitle().equals(title + "\n" + description)) {
                                    slideExists = true;
                                    break;
                                }
                            }
                            if (!slideExists) {
                                lstSlides.add(new Slide(imageUrl, title + "\n" + description));
                            }

// Check for duplicate movies
                            boolean movieExists = false;
                            for (Movie movie : lstMovies) {
                                if (movie.getTitle().equals(title) && movie.getThumbnail().equals(imageUrl)) {
                                    movieExists = true;
                                    break;
                                }
                            }
                            if (!movieExists) {
                                lstMovies.add(new Movie(title, description, imageUrl, "", "", video));
                            }

//                            if (!lstSlides.contains(new Slide(imageUrl, title + "\n" + description))) {
//                                lstSlides.add(new Slide(imageUrl, title + "\n" + description));
//                            }
//
//                            if (!lstMovies.contains(new Movie(title, description, imageUrl, "", "", video))) {
//                                lstMovies.add(new Movie(title, description, imageUrl, "", "", video));
//                            }
//                            lstSlides.add(new Slide(imageUrl, title + "\n" + description));
//                            lstMovies.add(new Movie(title,description,imageUrl,"","",video));
                        }



                        MovieAdapter movieAdapter = new MovieAdapter(this,lstMovies,this);

                        if (lstMovies != null && !lstMovies.isEmpty()) {
                            MoviesRV.setAdapter(movieAdapter);
                            MoviesRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                        }
                        if (lstSlides != null && !lstSlides.isEmpty()) {
                            SliderPagerAdapter adapter = new SliderPagerAdapter(this,lstSlides,this::onSlideClick);
                            sliderpager.setAdapter(adapter);
                        }

//                            textView.setText(title);
                    } catch (JSONException e) {

                        e.printStackTrace();
                        System.out.println(e.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(home.this,error.toString(),Toast.LENGTH_LONG);
                        Log.e("VolleyError", error.toString());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null); // Stop the sync when the activity is destroyed
    }

    private void scheduleSync() {
        PeriodicWorkRequest syncRequest = new PeriodicWorkRequest.Builder(SyncWorker.class, 15, TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance(this).enqueue(syncRequest);
    }
}

