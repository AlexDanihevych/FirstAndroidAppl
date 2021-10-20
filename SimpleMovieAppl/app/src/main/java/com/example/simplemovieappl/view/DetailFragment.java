package com.example.simplemovieappl.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.simplemovieappl.R;
import com.example.simplemovieappl.Visibility;
import com.example.simplemovieappl.model.Movie;

import java.util.Objects;


public class DetailFragment extends Fragment {

    TextView title;
    TextView overview;
    ImageView moviePoster;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detail_info_fragment, container,false);

        Movie movie = (Movie) requireArguments().getSerializable("movie");

        title = view.findViewById(R.id.detail_title);
        overview = view.findViewById(R.id.detail_overview);
        moviePoster = view.findViewById(R.id.detail_poster);

        title.setText(Objects.requireNonNull(movie).getTitle());
        overview.setText(movie.getOverview());
        Glide.with(getActivity().getApplicationContext())
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .into(moviePoster);

        return view;
    }

    @Override
    public void onDetach() {

        Visibility visibility = (Visibility) getActivity();
        visibility.makeVisible();
        super.onDetach();
    }
}
