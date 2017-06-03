package me.johngummadi.movies.views.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
//import com.lapism.searchview.SearchView;

import java.util.ArrayList;
import java.util.Arrays;
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
        implements IMoviesView, SwipeRefreshLayout.OnRefreshListener
{
    private final String ARG_MOVIE_LIST = "movielist";

    @BindView(R.id.svSearchMoviesView) SearchView _svSearchMoviesView;
    @BindView(R.id.rvMovies) RecyclerView _rvMovies;
    @BindView(R.id.srMoviesPullRefresh) SwipeRefreshLayout _srMoviesPullRefresh;
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
        init();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Object[] moviesArray = _movies.toArray();
        Parcelable[] pa = new Movie[moviesArray.length];
        System.arraycopy(moviesArray, 0, pa, 0, moviesArray.length);
        outState.putParcelableArray(ARG_MOVIE_LIST, pa);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState == null)
            return;
        /**
         * NOTE: Not sure why Java doesn't allow casting Parcelable[] to
         * Movie[] as Movie implements Parcelable. So had to do this crazy
         * conversion.
         * TODO: Figure a better way.
         */
        Parcelable[] pa = savedInstanceState.getParcelableArray(ARG_MOVIE_LIST);
        Movie[] moviesArr = new Movie[pa.length];
        System.arraycopy(pa, 0, moviesArr, 0, pa.length);
        _movies.clear();
        _movies.addAll(Arrays.asList(moviesArr));

        // Show movies
        _adapter.notifyDataSetChanged();
        showEmptyState(false);

        getPresenter().setSearchQuery(_svSearchMoviesView.getQuery().toString());
    }

    MovieAdapter.OnItemClickListener _itemClickListener = new MovieAdapter.OnItemClickListener() {
        @Override
        public void onClick(int pos, View itemView) {
            Movie movie = _movies.get(pos);
            //Toast.makeText(getContext(), movie.getTitle() + "\n\n" + movie.getOverview(), Toast.LENGTH_SHORT).show();
            if (_listener != null) {
                _listener.onMovieSelected(movie);
            }
        }
    };

    @Override
    public void onRefresh() {
        _svSearchMoviesView.clearFocus();
        getPresenter().searchButtonClicked();
    }

    private void init() {
        initUIControls();
        initList();
        initSearchView();
    }

    private void initUIControls() {
        if (_srMoviesPullRefresh != null) {
            _srMoviesPullRefresh.setOnRefreshListener(this);

            _srMoviesPullRefresh.setProgressBackgroundColorSchemeColor(
                    ContextCompat.getColor(getContext(), R.color.colorPrimary));

            _srMoviesPullRefresh.setColorSchemeColors(ContextCompat.getColor(
                    getContext(), android.R.color.white));

            _srMoviesPullRefresh.setProgressViewOffset(false, 0,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            24, getResources().getDisplayMetrics()));
        }
    }

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
                getPresenter().setSearchQuery(query);
                onRefresh();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //_svSearchMoviesView

        View closeButton = _svSearchMoviesView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        if (closeButton != null) {
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _svSearchMoviesView.setQuery("", false);
                    getPresenter().searchCleared();
                    _svSearchMoviesView.clearFocus();
                    _svSearchMoviesView.setIconified(true);

                }
            });
        }



        // TODO: Disabling voice for now
        //_svSearchMoviesView.setVoice(false);
    }

    private void sendScrollEvents() {
        int lastVisiblePos = _layoutManager.findLastCompletelyVisibleItemPosition();
        if (lastVisiblePos > 0 && lastVisiblePos == _movies.size()-1) {
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
        if (show)
            showEmptyState(false);
        _srMoviesPullRefresh.setRefreshing(show);
        //_pbLoad.setVisibility(show? View.VISIBLE : View.GONE);
    }

    @Override
    public void showSpinnerAtBottom(boolean show) {
        _pbLoadMore.setVisibility(show? View.VISIBLE : View.GONE);
    }

    @Override
    public void clearList() {
        _movies.clear();
        _adapter.notifyDataSetChanged();
        showEmptyState(true);
    }

    public void resetView() {
        _svSearchMoviesView.setQuery("", false);
        clearList();
    }

    @Override
    public boolean isListEmpty() {
        return (_movies.size() == 0);
    }

    private void showEmptyState(boolean show) {
        _rlEmptyStateMovies.setVisibility(show? View.VISIBLE : View.GONE);
    }
}
