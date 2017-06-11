package tobiapplications.com.moviebase.ui.movie_overview;

import java.util.ArrayList;

import tobiapplications.com.moviebase.listener.OnLoadMoreMoviesListener;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.MovieOverviewModel;

/**
 * Created by Tobias on 09.06.2017.
 */

public interface MovieOverview extends OnLoadMoreMoviesListener, OnMovieClickListener {
    void makeToast(String message);
    void findMyViews();
    void setGridViewAndAdapter();
    void showNetworkError(boolean isConnected);
    void setMovies(ArrayList<MovieOverviewModel> movies);
    void showLoading(boolean load);
    int getCurrentMovieSize();
}
