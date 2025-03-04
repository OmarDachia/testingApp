package com.example.testingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {

    private Context mContext ;
    private List<Slide> mList ;
    private OnSlideClickListener listener;

    public interface OnSlideClickListener {
        void onSlideClick(Slide slide);
    }


    public SliderPagerAdapter(Context mContext, List<Slide> mList, OnSlideClickListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View slideLayout = inflater.inflate(R.layout.slide_item,null);
//
//        ImageView slideImg = slideLayout.findViewById(R.id.slide_img);
//        TextView slideText = slideLayout.findViewById(R.id.slide_title);
//        slideImg.setImageResource(mList.get(position).getImage());
//        slideText.setText(mList.get(position).getTitle());
//
//        container.addView(slideLayout);
//
//
//        return slideLayout;



        View view = LayoutInflater.from(mContext).inflate(R.layout.slide_item, container, false);

        ImageView slideImg = view.findViewById(R.id.slide_img);
        TextView slideTitle = view.findViewById(R.id.slide_title);
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);

        Slide slide = mList.get(position);

        // Load image from URL using Glide
        Glide.with(mContext)
                .load(slide.getImage()) // Load the image URL
                .placeholder(R.drawable.slide1) // Placeholder image while loading
                .error(R.drawable.slide2) // Error image if URL fails
                .into(slideImg);

        slideTitle.setText(slide.getTitle());

        fab.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSlideClick(slide);
            }
        });

        container.addView(view);
        return view;


    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
