package me.johngummadi.movies.presenters;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import me.johngummadi.movies.models.Movie;
import me.johngummadi.movies.views.IMovieDetailsView;

/**
 * Created by johngummadi on 6/1/17.
 */

public interface IMovieDetailsPresenter extends MvpPresenter<IMovieDetailsView> {
    /**
     * TODO: For now there is no user interaction in the MovieDetails View,
     * that would require business logic. Therefore there is not much in this section.
     * When we add more functionality to MovieDetails view, we should apply
     * business logic to the user interactions here.
     */
    void initialized(Movie movie);
}
