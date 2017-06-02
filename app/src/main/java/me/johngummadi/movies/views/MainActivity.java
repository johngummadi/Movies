package me.johngummadi.movies.views;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import me.johngummadi.movies.R;
import me.johngummadi.movies.views.fragments.MoviesFragment;


public class MainActivity extends AppCompatActivity {
    //@BindView(R.id.svSearchMoviesView) SearchView _svSearchMoviesView;

    MoviesFragment _moviesFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        _moviesFragment = new MoviesFragment();
        fragmentTransaction.add(R.id.moviesFragmentContainer, _moviesFragment, "Movies");
        fragmentTransaction.commit();
        //initSearchView();
    }

//    private void initSearchView() {
//        _svSearchMoviesView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                /**
//                 * TODO: This is little weird now. Rethink this.
//                 * I had to put the SearchView in the Activity instead of Fragment because I
//                 * wanted to put the search on Actionbar to allow for clipping of Actionbar.
//                 * So for now, I am sending this event to the MoviesFragment (which implements
//                 * IMovieView) and from there the MoviesFragment will pass that over to the presenter.
//                 * Little round about, but for now it works. Need to rethink this.
//                 */
//                _moviesFragment.onSearchRequested(query);
//                _svSearchMoviesView.clearFocus();
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//
//        // TODO: Disabling voice for now
//        _svSearchMoviesView.setVoice(false);
//    }
}
