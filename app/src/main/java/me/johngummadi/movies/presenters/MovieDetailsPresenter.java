package me.johngummadi.movies.presenters;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import me.johngummadi.movies.models.Movie;
import me.johngummadi.movies.views.IMovieDetailsView;

/**
 * Created by johngummadi on 6/1/17.
 */

public class MovieDetailsPresenter extends MvpBasePresenter<IMovieDetailsView> implements IMovieDetailsPresenter {

    public MovieDetailsPresenter() {
        ;
    }

    @Override
    public void attachView(IMovieDetailsView view) {
        super.attachView(view);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
    }

    @Override
    public void initialized(Movie movie) {
        if (isViewAttached()) {
            if (movie != null)
                getView().displayMovieDetails();
            else
                getView().showError("Sorry, could not fetch movie details");
        }
    }
}
