package me.johngummadi.movies.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.lapism.searchview.SearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.johngummadi.movies.R;
import me.johngummadi.movies.models.Movie;
import me.johngummadi.movies.presenters.IMoviesPresenter;
import me.johngummadi.movies.presenters.MoviesPresenter;
import me.johngummadi.movies.views.IMoviesView;
import me.johngummadi.movies.views.adapters.MovieAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment
        extends MvpFragment<IMoviesView, IMoviesPresenter>
        implements IMoviesView
{
    @BindView(R.id.svSearchMoviesView) SearchView _svSearchMoviesView;
    @BindView(R.id.rvMovies) RecyclerView _rvMovies;
    @BindView(R.id.pbLoad) ProgressBar _pbLoad;
    @BindView(R.id.pbLoadMore) ProgressBar _pbLoadMore;
    @BindView(R.id.rlEmptyStateMovies) RelativeLayout _rlEmptyStateMovies;

    LinearLayoutManager _layoutManager;
    List<Movie> _movies = new ArrayList<>();
    MovieAdapter _adapter;

    // This is listener for Fragment events that Activity might be interested in
    public interface Listener {
        void onMovieSelected(Movie movie);
        // TODO: Add more events as needed
    }

    Listener _listener;

    public MoviesFragment() {
    }

    public void setListener(Listener listener) {
        _listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList();
        initSearchView();
    }

    MovieAdapter.OnItemClickListener _itemClickListener = new MovieAdapter.OnItemClickListener() {
        @Override
        public void onClick(int pos, View itemView) {
            Movie movie = _movies.get(pos);
            Toast.makeText(getContext(), movie.getTitle() + "\n\n" + movie.getOverview(), Toast.LENGTH_SHORT).show();
            if (_listener != null) {
                _listener.onMovieSelected(movie);
            }
        }
    };

    private void initList() {
        _adapter = new MovieAdapter(_movies, _itemClickListener);
        _rvMovies.setAdapter(_adapter);
        _layoutManager = new LinearLayoutManager(getContext());
        _rvMovies.setLayoutManager(_layoutManager);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            _rvMovies.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    sendScrollEvents();
                }
            });
        } else{
            _rvMovies.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    sendScrollEvents();
                }
            });
        }
    }

    private void initSearchView() {
        _svSearchMoviesView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                _svSearchMoviesView.clearFocus();
                getPresenter().searchButtonClicked(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // TODO: Disabling voice for now
        _svSearchMoviesView.setVoice(false);
    }

    private void sendScrollEvents() {
        if (_layoutManager.findLastCompletelyVisibleItemPosition() == _movies.size()-1) {
            getPresenter().scrolledToEnd();
        }
    }

    @Override
    public IMoviesPresenter createPresenter() {
        return new MoviesPresenter();
    }

    @Override
    public void displayMovies(List<Movie> movies) {
        _movies.addAll(movies);
        _adapter.notifyDataSetChanged();
        showEmptyState(false);
    }

    @Override
    public void displayError(String error) {
        //TODO: Implement this
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        showEmptyState(false);
    }

    @Override
    public void showLoadingSpinner(boolean show) {
        showEmptyState(false);
        _pbLoad.setVisibility(show? View.VISIBLE : View.GONE);
    }

    @Override
    public void showSpinnerAtBottom(boolean show) {
        _pbLoadMore.setVisibility(show? View.VISIBLE : View.GONE);
    }

    @Override
    public void clear() {
        _movies.clear();
        _adapter.notifyDataSetChanged();
        showEmptyState(true);
    }

    private void showEmptyState(boolean show) {
        _rlEmptyStateMovies.setVisibility(show? View.VISIBLE : View.GONE);
    }
}
