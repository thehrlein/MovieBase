package tobiapplications.com.moviebase.ui.movie_overview;

import tobiapplications.com.moviebase.model.MovieOverviewResponse;

/**
 * Created by Tobias on 10.06.2017.
 */

public interface MoviePresenter {
    void loadMovies();
    boolean noMoviesShown();
    boolean hasInternetConnection();
    void isConnectedToInternet(boolean connected);
    void requestMovieDownload();
    void displayMovies(MovieOverviewResponse movieOverviewResponse);
    void loadMoreMovies();
    void displayError();
}
