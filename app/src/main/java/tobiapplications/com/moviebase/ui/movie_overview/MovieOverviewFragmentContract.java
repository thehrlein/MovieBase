package tobiapplications.com.moviebase.ui.movie_overview;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;

import java.util.ArrayList;

import tobiapplications.com.moviebase.listener.OnLoadMoreMoviesListener;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.MovieOverviewModel;
import tobiapplications.com.moviebase.model.MovieOverviewResponse;

/**
 * Created by Tobias on 09.06.2017.
 */

public interface MovieOverviewFragmentContract {

    interface View extends OnLoadMoreMoviesListener, OnMovieClickListener {
        void findMyViews();
        void setGridViewAndAdapter();
        void showNetworkError(boolean noNetwork);
        void setMovies(ArrayList<MovieOverviewModel> movies);
        void insertLoadingItem();
        void showLoading(boolean load);
        int getCurrentMovieSize();
    }

    interface Presenter {
        void loadMovies();
        boolean noMoviesShown();
        boolean hasInternetConnection();
        void isConnectedToInternet(boolean connected);
        void requestMovieDownload();
        void displayMovies(MovieOverviewResponse movieOverviewResponse);
        void loadMoreMovies();
        void displayError();
    }

    interface DatabaseView extends LoaderManager.LoaderCallbacks<Cursor>, OnMovieClickListener {
        void findMyViews();
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
