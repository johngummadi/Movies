package me.johngummadi.movies.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.johngummadi.movies.R;
import me.johngummadi.movies.models.Movie;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends Fragment {
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

    public void update(Movie movie) {
        _movie = movie;
        render();
    }

    private void render() {
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
}
