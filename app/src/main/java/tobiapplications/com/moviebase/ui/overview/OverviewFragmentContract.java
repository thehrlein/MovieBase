package tobiapplications.com.moviebase.ui.overview;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;

import java.util.ArrayList;

import tobiapplications.com.moviebase.listener.OnLoadMoreMoviesListener;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.listener.OnOverviewMovieLoadListener;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;

/**
 * Created by Tobias on 09.06.2017.
 */

public interface OverviewFragmentContract {

    interface View extends OnLoadMoreMoviesListener, OnMovieClickListener {
        void setGridViewAndAdapter();
        void showNetworkError(boolean noNetwork);
        void setMovies(ArrayList<MovieOverviewModel> movies);
        void insertLoadingItem();
        void showLoading(boolean load);
        int getCurrentMovieSize();
    }

    interface Presenter extends OnOverviewMovieLoadListener {
        void loadMovies();
        boolean noMoviesShown();
        boolean hasInternetConnection();
        void isConnectedToInternet(boolean connected);
        void loadMoreMovies();
    }

    interface DatabaseView extends LoaderManager.LoaderCallbacks<Cursor>, OnMovieClickListener {
        void setGridViewAndAdapter();
        void showNetworkError(boolean noNetwork);
        void setMovies(ArrayList<MovieOverviewModel> movies);
        void showLoading(boolean load);
        int getCurrentMovieSize();
        void startLoader();
        void displayNoFavoriteMoviesText(boolean noFavoriteMovies);
    }

    interface DatabasePresenter {
        void loadMoviesFromDatabase();
        boolean hasInternetConnection();
        void isConnectedToInternet(boolean connected);
        void createMovieListFromCursor(Cursor data);
        ArrayList<Integer> extractGenres(Cursor data);
        MovieOverviewModel buildMovieFromCursor(Cursor data, ArrayList<Integer> genres, boolean adult);
        void onDatabaseLoadFinished(Cursor data);
    }
}
