package com.example.simplemovieappl;


import com.example.simplemovieappl.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

      @GET("/3/trending/movie/day")
      Call<MoviesResponse> getTrendingMovies(@Query("api_key") String apiKey,
                                             @Query("page") int page);

}
