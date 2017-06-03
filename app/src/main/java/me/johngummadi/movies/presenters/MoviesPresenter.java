package me.johngummadi.movies.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
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
    private final int MIN_QUERY_LEN = 3;

    public MoviesPresenter() {
        _page = 1;
        _query = "";
    }

    private void reset() {
        _page = 1;
        _query = "";
    }

    @Override
    public void searchButtonClicked() {
        if (isViewAttached()) {
            hideAllSpinners();
            // Validate the query string
            if (_query==null || _query.length() < MIN_QUERY_LEN) {
                getView().displayError("Invalid query. Please type at-least three characters");
                return;
            }
            _page = 1; //reset page.
            getView().clear();
            getView().showLoadingSpinner(true);
            searchMovies(_query);
        }
    }

    @Override
    public void setSearchQuery(String query) {
        _query = query;
    }

    @Override
    public void searchCleared() {
        if (isViewAttached()) {
            reset();
            hideAllSpinners();
            getView().clear();
        }
    }

    @Override
    public void scrolledToEnd() {
        if (isViewAttached()) {
            hideAllSpinners();
            getView().showSpinnerAtBottom(true);
            _page ++;
            searchMovies(_query);
        }
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
                        getView().displayMovies(response.body().getResults());
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
