package me.johngummadi.movies.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by johngummadi on 6/1/17.
 */

public interface IMovieDetailsView extends MvpView {
    void displayMovieDetails();
    void showError(String error);
}
