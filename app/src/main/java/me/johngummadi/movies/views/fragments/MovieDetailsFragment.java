package me.johngummadi.movies.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.johngummadi.movies.R;
import me.johngummadi.movies.models.Movie;
import me.johngummadi.movies.presenters.IMovieDetailsPresenter;
import me.johngummadi.movies.presenters.MovieDetailsPresenter;
import me.johngummadi.movies.views.IMovieDetailsView;

/**
 * NOTE: This MVP here is kinda forced. This fragment really doesn't need
 * a presenter with current functionality (and it feels round about).
 * But I forced it to keep in line with the rest of the Architecture.
 */
public class MovieDetailsFragment
        extends MvpFragment<IMovieDetailsView, IMovieDetailsPresenter>
        implements IMovieDetailsView
{
    private final String ARG_MOVIE = "movie";
    Movie _movie;

    @BindView(R.id.ivMovieDetailPosterImage) ImageView _ivMovieDetailPosterImage;
    @BindView(R.id.tvMovieDetailTitle) TextView _tvMovieDetailTitle;
    @BindView(R.id.tvMovieDetailReleaseDate) TextView _tvMovieDetailReleaseDate;
    @BindView(R.id.rbMovieDetailRating) RatingBar _rbMovieDetailRating;
    @BindView(R.id.tvMovieDetailOverview) TextView _tvMovieDetailOverview;

    // This is listener for Fragment events that Activity might be interested in
    public interface Listener {
        // TODO: Add more events as needed
    }

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public IMovieDetailsPresenter createPresenter() {
        return new MovieDetailsPresenter();
    }

    MovieDetailsFragment.Listener _listener;
    public void setListener(MovieDetailsFragment.Listener listener) {
        _listener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_MOVIE, _movie);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState == null)
            return;
        _movie = savedInstanceState.getParcelable(ARG_MOVIE);
        render();
    }

    @Override
    public void displayMovieDetails() {
        _tvMovieDetailTitle.setText(_movie.getTitle());
        Glide.with(getContext())
                .load(_movie.getPosterPathFull())
                .skipMemoryCache(true)
                .placeholder(R.drawable.default_poster)
                .into(_ivMovieDetailPosterImage);
        _tvMovieDetailReleaseDate.setText("Released on: " + _movie.getReleaseDateString());
        _tvMovieDetailOverview.setText(_movie.getOverview());
        _rbMovieDetailRating.setRating((float) _movie.getVoteAverage()/2);
    }

    @Override
    public void showError(String error) {
        // TODO: Show the error on the view
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    public void update(Movie movie) {
        _movie = movie;
        getPresenter().initialized(_movie);
    }

    private void render() {

    }
}
