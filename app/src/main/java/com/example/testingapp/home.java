package com.example.testingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.testingapp.Slide;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.widget.Toast;

public class home extends AppCompatActivity implements MovieItemClickListener {
    private List<Slide> lstSlides ;
    private ViewPager sliderpager;
    private TabLayout indicator;
    private RecyclerView MoviesRV ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sliderpager = findViewById(R.id.slider_pager) ;
        indicator = findViewById(R.id.indicator);
        MoviesRV = findViewById(R.id.Rv_movies);

        // prepare a list of slides ..
        lstSlides = new ArrayList<>() ;
        lstSlides.add(new Slide(R.drawable.slide1,"Slide Title \nmore text here"));
        lstSlides.add(new Slide(R.drawable.slide2,"Slide Title \nmore text here"));
        lstSlides.add(new Slide(R.drawable.slide1,"Slide Title \nmore text here"));
        lstSlides.add(new Slide(R.drawable.slide2,"Slide Title \nmore text here"));
        SliderPagerAdapter adapter = new SliderPagerAdapter(this,lstSlides);
        sliderpager.setAdapter(adapter);
        // setup timer
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new home.SliderTimer(),4000,6000);
        indicator.setupWithViewPager(sliderpager,true);

        // Recyclerview Setup
        // ini data

        List<Movie> lstMovies = new ArrayList<>();
        lstMovies.add(new Movie("Moana",R.drawable.moana,R.drawable.spidercover));
        lstMovies.add(new Movie("Black P",R.drawable.blackp,R.drawable.spidercover));
        lstMovies.add(new Movie("The Martian",R.drawable.themartian));
        lstMovies.add(new Movie("The Martian",R.drawable.themartian));
        lstMovies.add(new Movie("The Martian",R.drawable.themartian));
        lstMovies.add(new Movie("The Martian",R.drawable.themartian));

        MovieAdapter movieAdapter = new MovieAdapter(this,lstMovies,this);
        MoviesRV.setAdapter(movieAdapter);
        MoviesRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

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
        // lets crezte the animation
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(home.this,
                movieImageView,"sharedName");

        startActivity(intent,options.toBundle());



        // i l make a simple test to see if the click works

        Toast.makeText(this,"item clicked : " + movie.getTitle(),Toast.LENGTH_LONG).show();
        // it works great


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
}

