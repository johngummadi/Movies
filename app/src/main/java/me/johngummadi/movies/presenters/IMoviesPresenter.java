package me.johngummadi.movies.presenters;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import me.johngummadi.movies.views.IMoviesView;

/**
 * Created by johngummadi on 6/1/17.
 */

public interface IMoviesPresenter extends MvpPresenter<IMoviesView> {
    void setSearchQuery(String query);
    void searchButtonClicked();
    void searchCleared();
    void scrolledToEnd();
}
