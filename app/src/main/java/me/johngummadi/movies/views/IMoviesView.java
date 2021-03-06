package me.johngummadi.movies.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import me.johngummadi.movies.models.Movie;

/**
 * Created by johngummadi on 6/1/17.
 */

public interface IMoviesView extends MvpView {
    String getQueryString();
    void displayMovies(List<Movie> movies);
    void displayError(int resId);
    void displayError(String error);
    void launchMovieDetails(Movie movie);
    void showLoadingSpinner(boolean show);
    void showSpinnerAtBottom(boolean show);
    // This just clears the list
    void clearList();
    // The resetView clears both search query, and the list
    void resetView();
    boolean isListEmpty();
}
