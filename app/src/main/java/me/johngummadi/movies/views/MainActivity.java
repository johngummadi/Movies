package me.johngummadi.movies.views;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import me.johngummadi.movies.R;
import me.johngummadi.movies.models.Movie;
import me.johngummadi.movies.views.fragments.MovieDetailsFragment;
import me.johngummadi.movies.views.fragments.MoviesFragment;


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

    private void init() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        _moviesFragment = (MoviesFragment) fragmentManager.findFragmentById(R.id.movie_list_fragment);
        _moviesFragment.setListener(this);

        _movieDetailsFragment = (MovieDetailsFragment) fragmentManager.findFragmentById(R.id.movie_details_fragment);
        if (_movieDetailsFragment != null) {
            _movieDetailsFragment.setListener(this);
        }

        /*
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        _moviesFragment = new MoviesFragment();
        fragmentTransaction.add(R.id.moviesFragmentContainer, _moviesFragment, "Movies");
        fragmentTransaction.commit();
        */
        //initSearchView();
    }

    @Override
    public void onMovieSelected(Movie movie) {
        if (_movieDetailsFragment != null) {
            // This is a tablet, show the details on the side pane
            _movieDetailsFragment.update(movie);
        }
        else {
            MovieDetailsActivity.launch(movie, this);
        }
    }
}
