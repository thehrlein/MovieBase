package tobiapplications.com.moviebase.ui.movie_overview;

import java.util.ArrayList;

import tobiapplications.com.moviebase.model.Movie;

/**
 * Created by Tobias on 09.06.2017.
 */

public interface MovieOverview {
    void makeToast(String message);
    void findMyViews();
    void setGridViewAndAdapter();
    void showNoNetworkError(boolean isConnected);
    void setMovies(ArrayList<Movie> movies);
    void showLoading(boolean load);
}
