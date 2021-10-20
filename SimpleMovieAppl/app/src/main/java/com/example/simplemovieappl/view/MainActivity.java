package com.example.simplemovieappl.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.simplemovieappl.Visibility;
import com.example.simplemovieappl.contract.MainActivityContract;
import com.example.simplemovieappl.adapter.MovieAdapter;
import com.example.simplemovieappl.R;
import com.example.simplemovieappl.model.Movie;
import com.example.simplemovieappl.presenter.MainViewPresenter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MainActivityContract.View,
        MovieAdapter.OnMovieListener, Visibility {

    MainActivityContract.Presenter mPresenter;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    List<Movie> movieList;
    MovieAdapter movieAdapter;
    MovieAdapter.OnMovieListener onMovieListener;
    LinearLayoutManager linearLayoutManager;
    int currentPage = 1;
    int visibleItemCount, pastVisibleItems, totalItemCount;
    Boolean loading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainViewPresenter(this);
        onMovieListener = this;
    }

    @Override
    public void setupUI(){

        progressDialog = new ProgressDialog(this);
        recyclerView = findViewById((R.id.recycler_view));
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieList,this);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){

                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if(!loading){
                        if((visibleItemCount + pastVisibleItems) >= totalItemCount - 3){
                            loading = true;
                            currentPage++;
                            mPresenter.getTrendingMovies(getAPIKey(), currentPage);
                        }
                    }
                }
            }
        });
    }

    @Override
    public String getAPIKey() {
        return getString(R.string.api_key);
    }

    @Override
    public void displayMovieData(List<Movie> list) {

        Log.d("mvp", movieList.size() + "");
        movieList.addAll(list);
        movieAdapter.notifyDataSetChanged();
        loading = false;
    }

    @Override
    public void showMessage(String msg) {

        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {

        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.setMessage("Loading ...");
        } else {
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading ... ");
            progressDialog.setCancelable(false);
        }

        try {
            progressDialog.show();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void hideProgressDialog() {

        try {

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog.hide();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMovieClick(int position) {

        recyclerView.setVisibility(View.GONE);
        Movie movie = movieList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("movie", movie);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, DetailFragment.class, bundle)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void makeVisible() {
        recyclerView.setVisibility(View.VISIBLE);
    }
}