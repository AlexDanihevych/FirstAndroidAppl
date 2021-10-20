package com.example.simplemovieappl.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.simplemovieappl.R;
import com.example.simplemovieappl.model.Movie;

import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private final List<Movie> movieList;
    private final OnMovieListener mOnMovieListener;

    public  MovieAdapter(List<Movie> list, OnMovieListener onMovieListener){
        movieList = list;
        mOnMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate((R.layout.movie_item),parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {

        Movie movie = movieList.get(position);
        holder.movieName.setText(movie.getTitle());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500"+movie.getPosterPath())
                .into(holder.moviePoster);
        holder.itemView.setOnClickListener(v ->mOnMovieListener.onMovieClick(position));
    }
    @Override
    public int getItemCount() {
        return(null != movieList ? movieList.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface OnMovieListener{
        void onMovieClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView moviePoster;
        final TextView movieName;
        public ViewHolder(View view){
            super(view);
            movieName = view.findViewById(R.id.movie_name);
            moviePoster = view.findViewById(R.id.movie_poster);
        }
    }
}
