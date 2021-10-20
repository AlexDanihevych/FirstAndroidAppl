package com.example.simplemovieappl.model;

import com.example.simplemovieappl.ApiInterface;
import com.example.simplemovieappl.contract.MainActivityContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieAPI implements MainActivityContract.Model {

    @Override
    public void getTrendingMovies(String apiKey,
                                  int page,
                                  final MainActivityContract.APIListener listener) {

        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface service = retrofit.create(ApiInterface.class);

            Call<MoviesResponse> call = service.getTrendingMovies(apiKey, page);
            call.enqueue(new Callback<MoviesResponse>() {

                @Override
                public void onResponse(Call<MoviesResponse> call,
                                       Response<MoviesResponse> response) {

                    if (response.isSuccessful()) {
                        listener.onSuccess(response);
                    } else {
                        listener.onError(response);
                    }
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    listener.onFailure(t);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}