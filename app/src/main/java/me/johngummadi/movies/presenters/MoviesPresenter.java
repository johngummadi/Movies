package me.johngummadi.movies.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import me.johngummadi.movies.R;
import me.johngummadi.movies.models.Movie;
import me.johngummadi.movies.retrofit.MoviesSearchResponse;
import me.johngummadi.movies.retrofit.RestClient;
import me.johngummadi.movies.views.IMoviesView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by johngummadi on 6/1/17.
 */

public class MoviesPresenter extends MvpBasePresenter<IMoviesView> implements IMoviesPresenter {
    private Call<MoviesSearchResponse> _searchCall;
    private String _query;
    private int _page;
    private int _totalPages;
    private final int MIN_QUERY_LEN = 3;

    public MoviesPresenter() {
        _page = 1;
        _query = "";
        _totalPages = -1;
    }

    private void reset() {
        _page = 1;
        _query = "";
        _totalPages = -1;
    }

    @Override
    public void onSearchButtonClicked() {
        if (isViewAttached()) {
            // We get query once here, and use it for subsequent lazy load of more movies or pull to refresh
            _query = getView().getQueryString();
            hideAllSpinners();
            // Validate the query string
            if (_query==null || _query.length() < MIN_QUERY_LEN) {
                getView().displayError(R.string.error_query_too_short);
                return;
            }
            _page = 1; //reset page.
            _totalPages = -1;
            getView().clearList();
            getView().showLoadingSpinner(true);
            searchMovies(_query);
        }
    }

    @Override
    public void onSearchCleared() {
        if (isViewAttached()) {
            reset();
            hideAllSpinners();
            getView().clearList();
        }
    }

    @Override
    public void onScrolledToEnd() {
        if (isViewAttached()) {
            // Do not attemot to load more if the current page reached the end of possible pages.
            if (_page < _totalPages) {
                hideAllSpinners();
                getView().showSpinnerAtBottom(true);
                _page++;
                searchMovies(_query);
            }
        }
    }

    @Override
    public void onMovieSelected(Movie movie) {
        getView().launchMovieDetails(movie);
    }

    @Override
    public void detachView(boolean retainInstance) {
        cancelSearchIfAppropriate();
        super.detachView(retainInstance);
    }

    private void cancelSearchIfAppropriate() {
        if (_searchCall!=null && _searchCall.isExecuted() && !_searchCall.isCanceled())
            _searchCall.cancel();
    }

    private void searchMovies(String query) {
        cancelSearchIfAppropriate();
        _searchCall = RestClient.getApi().searchMovies(query, _page);
        _searchCall.enqueue(new Callback<MoviesSearchResponse>() {
            @Override
            public void onResponse(Call<MoviesSearchResponse> call, Response<MoviesSearchResponse> response) {
                if (isViewAttached()) {
                    // NOTE: For some reason even errors are coming to onResponse where body is null. Therefore this check.
                    if (response!=null && response.body()!=null) {
                        List<Movie> results = response.body().getResults();
                        _totalPages = response.body().getTotalPages();
                        if (results.size() == 0)
                            getView().displayError(R.string.error_no_results);
                        else
                            getView().displayMovies(results);
                    }
                    hideAllSpinners();
                }
            }

            @Override
            public void onFailure(Call<MoviesSearchResponse> call, Throwable t) {
                if (isViewAttached()) {
                    // TODO: translate the exception to application error message.
                    // TODO: This is a dirty way of avoiding showing errors for cancelling od=f previous requests. Fix it.
                    if (t.getMessage()!=null && t.getMessage().compareToIgnoreCase("Canceled") != 0)
                        getView().displayError(t.getMessage());
                    hideAllSpinners();
                }
            }
        });
    }

    private void hideAllSpinners() {
        if (isViewAttached()) {
            getView().showLoadingSpinner(false);
            getView().showSpinnerAtBottom(false);
        }
    }
}
