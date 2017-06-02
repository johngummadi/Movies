package me.johngummadi.movies.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import me.johngummadi.movies.models.Movie;

/**
 * Created by johngummadi on 6/1/17.
 */

public interface IMoviesView extends MvpView {
    void displayMovies(List<Movie> movies);
    void displayError(String error);
    void showLoadingSpinner(boolean show);
    void showSpinnerAtBottom(boolean show);
    void clear();
}
