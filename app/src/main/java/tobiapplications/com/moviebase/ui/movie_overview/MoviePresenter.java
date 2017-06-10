package tobiapplications.com.moviebase.ui.movie_overview;

import tobiapplications.com.moviebase.model.MovieResponse;

/**
 * Created by Tobias on 10.06.2017.
 */

public interface MoviePresenter {
    void loadMovies();
    boolean hasInternetConnection();
    void isConnectedToInternet(boolean connected);
    void requestMovieDownload();
    void displayMovies(MovieResponse movieResponse);
}
