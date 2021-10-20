package com.example.simplemovieappl.presenter;

import android.util.Log;

import com.example.simplemovieappl.contract.MainActivityContract;
import com.example.simplemovieappl.model.MovieAPI;
import com.example.simplemovieappl.model.MoviesResponse;

import retrofit2.Response;

public class MainViewPresenter implements MainActivityContract.Presenter,
        MainActivityContract.APIListener {

    final MainActivityContract.View mView;
    final MainActivityContract.Model mModel;

    public MainViewPresenter(MainActivityContract.View view){

        mView = view;
        mModel = new MovieAPI();
        mView.setupUI();
        getTrendingMovies(mView.getAPIKey(),1);
    }

    @Override
    public void getTrendingMovies(String apiKey, int page) {

        mView.showProgressDialog();
        mModel.getTrendingMovies(apiKey, page,this);
    }

    @Override
    public void onSuccess(Response<MoviesResponse> response) {

        Log.d("mvp", response.body().getResults().size()+ "");
        mView.hideProgressDialog();
        mView.displayMovieData(response.body().getResults());
    }

    @Override
    public void onError(Response<MoviesResponse> response) {

        mView.hideProgressDialog();
        mView.showMessage("Error Occurred");
    }

    @Override
    public void onFailure(Throwable t) {

        mView.hideProgressDialog();
        mView.showMessage(t.getMessage());
    }
}
