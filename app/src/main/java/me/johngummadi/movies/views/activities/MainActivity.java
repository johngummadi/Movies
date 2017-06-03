package me.johngummadi.movies.views.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import me.johngummadi.movies.R;
import me.johngummadi.movies.models.Movie;
import me.johngummadi.movies.views.fragments.MovieDetailsFragment;
import me.johngummadi.movies.views.fragments.MoviesFragment;

/**
 * NOTE: I haven't added MVP here to Activity as the Fragment acts as
 * a View and Activity is just a container here.
 */
public class MainActivity extends AppCompatActivity
        implements MoviesFragment.Listener, MovieDetailsFragment.Listener {
    //@BindView(R.id.svSearchMoviesView) SearchView _svSearchMoviesView;

    MoviesFragment _moviesFragment;
    MovieDetailsFragment _movieDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void onBackPressed() {
        // Pressing back here would clear the list if list is not empty, else finish the Activity.
        if (_moviesFragment.isListEmpty())
            super.onBackPressed();
        else
            _moviesFragment.resetView();
    }

    private void init() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        _moviesFragment = (MoviesFragment) fragmentManager.findFragmentById(R.id.movie_list_fragment);
        _moviesFragment.setListener(this);

        _movieDetailsFragment = (MovieDetailsFragment) fragmentManager.findFragmentById(R.id.movie_details_fragment);
        if (_movieDetailsFragment != null) {
            _movieDetailsFragment.setListener(this);
        }
    }

    @Override
    public void onMovieSelected(Movie movie) {
        /**
         * TODO: This should go back to the presenter and the
         * preserter should decide what to do with the movie selection.
         * For the lack of time, I just hacked this up.
         */
        if (_movieDetailsFragment != null && _movieDetailsFragment.isAdded()) {
            // This is a tablet, show the details on the side pane
            _movieDetailsFragment.update(movie);
        }
        else {
            MovieDetailsActivity.launch(movie, this);
        }
    }
}
