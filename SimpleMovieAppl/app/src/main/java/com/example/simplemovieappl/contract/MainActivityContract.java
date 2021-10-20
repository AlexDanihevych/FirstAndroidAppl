package com.example.simplemovieappl.contract;


import com.example.simplemovieappl.model.Movie;
import com.example.simplemovieappl.model.MoviesResponse;

import java.util.List;

import retrofit2.Response;


public interface MainActivityContract {

    interface Model {
        void getTrendingMovies(String apiKey, int page,final APIListener listener);
    }

    interface View{
        void setupUI();
        String getAPIKey();
        void displayMovieData(List<Movie> movieList);
        void showMessage(String msg);
        void showProgressDialog();
        void hideProgressDialog();
    }

    interface Presenter{
        void getTrendingMovies(String apiKey, int page);
    }

    interface APIListener{
        void onSuccess (Response<MoviesResponse> response);
        void onError (Response<MoviesResponse> response);
        void onFailure (Throwable t);
    }
}
