package com.example.testingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieCategoryAdapter extends RecyclerView.Adapter<MovieCategoryAdapter.ViewHolder> {
    private List<MovieCat> movieCategories;
    Context context ;

    public MovieCategoryAdapter(Context context, List<MovieCat> movieCategories) {
        this.context = context;
        this.movieCategories = movieCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvCategoryName.setText(movieCategories.get(position).getTitle());
        holder.CategoryImage.setImageResource(movieCategories.get(position).getThumbnail());
//        Glide.with(context)
//                .load(movieCategories.get(position)) // Load URL
//                .placeholder(R.drawable.spidercover) // Optional: Loading placeholder
//                .error(R.drawable.spidercover) // Optional: Error image if the URL fails
//                .into(holder.CategoryImage);

    }

    @Override
    public int getItemCount() {
        return movieCategories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        ImageView CategoryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            CategoryImage = itemView.findViewById(R.id.item_movie_img);
        }
    }
}
