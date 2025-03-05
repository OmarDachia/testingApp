package com.example.testingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class movie_detail extends AppCompatActivity {
    private ImageView MovieThumbnailImg,MovieCoverImg;
    private TextView tv_title,tv_description;
    private FloatingActionButton play_fab;
    String vid_url = "", movieTitle = "", imageResourceId ="", imagecover ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        FloatingActionButton playFabButton = findViewById(R.id.play_fab);

        iniViews();

        playFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(movie_detail.this,MainActivity.class);
                // send movie information to deatilActivity
                intent.putExtra("title",movieTitle);
                intent.putExtra("video",vid_url);
                // lets crezte the animation
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(movie_detail.this,
//                        movieImageView,"sharedName");

//                startActivity(intent,options.toBundle());
                startActivity(intent);
            }
        });


    }

    void iniViews() {
        play_fab = findViewById(R.id.play_fab);
        movieTitle = getIntent().getExtras().getString("title");
        imageResourceId = getIntent().getExtras().getString("imgURL");
        imagecover = getIntent().getExtras().getString("imgCover");
        vid_url = getIntent().getExtras().getString("video");

        MovieThumbnailImg = findViewById(R.id.detail_movie_img);
        Glide.with(this).load(imageResourceId).into(MovieThumbnailImg);
//        MovieThumbnailImg.setImageResource(imageResourceId);
//        Glide.with(this)
//                .load(imageResourceId) // Load image from URL
//                .placeholder(R.drawable.spidercover) // Optional: Placeholder while loading
//                .error(R.drawable.spidercover) // Optional: Error image if URL fails
//                .into(MovieThumbnailImg);

        MovieCoverImg = findViewById(R.id.detail_movie_cover);
        Glide.with(this).load(imagecover).into(MovieCoverImg);
        tv_title = findViewById(R.id.detail_movie_title);
        tv_title.setText(movieTitle);
//        getSupportActionBar().setTitle("movieTitle");
        tv_description = findViewById(R.id.detail_movie_desc);
        // setup animation
        MovieCoverImg.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        play_fab.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
    }
}