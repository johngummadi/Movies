package me.johngummadi.movies.presenters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import me.johngummadi.movies.R;
import me.johngummadi.movies.views.IMoviesView;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by johngummadi on 6/3/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class MoviesPresenterTest {
    @Mock
    private IMoviesView _view;

    private MoviesPresenter _presenter;
    @Before
    public void setUp() throws Exception {
        _presenter = new MoviesPresenter();
        _presenter.attachView(_view);
    }

    @Test
    public void shouldShowErrorIfQueryStringIsTooSmall() throws Exception {
        when(_view.getQueryString()).thenReturn("he");
        _presenter.onSearchButtonClicked();
        verify(_view).displayError(R.string.error_query_too_short);
    }

    @Test
    public void shouldNotShowErrorIfQueryStringIsValid() throws Exception {
        when(_view.getQueryString()).thenReturn("hello");
        _presenter.onSearchButtonClicked();
        verify(_view, never()).displayError(R.string.error_query_too_short);
    }

    // TODO: Add more tests...
}