package com.example.testingapp;

import android.widget.ImageView;

import com.example.testingapp.Movie;

public interface MovieItemClickListener {

    void onMovieClick(Movie movie, ImageView movieImageView); // we will need the imageview to make the shared animation between the two activity

}