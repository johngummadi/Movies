package me.johngummadi.movies.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import me.johngummadi.movies.R;
import me.johngummadi.movies.models.Movie;
import me.johngummadi.movies.views.fragments.MovieDetailsFragment;

public class MovieDetailsActivity extends AppCompatActivity {
    public final static String ARG_MOVIE = "movie";
    MovieDetailsFragment _movieDetailsFragment;

    /**
     * Gives an easy way to start MovieDetailsActivity
     * @param movie Movie whose details to be displayed
     * @param from The context from where you're launching
     *             this (typically activity's or fragment's context)
     */
    public static void launch(Movie movie, Context from) {
        Intent intent = new Intent(from, MovieDetailsActivity.class);
        intent.putExtra(ARG_MOVIE, movie);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        init();
    }

    private void init() {
        // Extract movie from the bundle
        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(ARG_MOVIE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        _movieDetailsFragment = (MovieDetailsFragment) fragmentManager.findFragmentById(R.id.movie_details_fragment);
        _movieDetailsFragment.update(movie);
    }
}
