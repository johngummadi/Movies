package me.johngummadi.movies.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.johngummadi.movies.R;
import me.johngummadi.movies.models.Movie;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends Fragment {
    @BindView(R.id.ivBackdropImage) ImageView _ivBackdropImage;
    @BindView(R.id.tvMovieDetailTitle) TextView _tvMovieDetailTitle;

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

    public void update(Movie movie) {
        _tvMovieDetailTitle.setText(movie.getTitle());
        // TODO: Add more details
    }
}
