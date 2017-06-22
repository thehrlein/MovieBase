package tobiapplications.com.moviebase.listener;

import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;

/**
 * Created by Tobias on 15.06.2017.
 */

public interface OnOverviewMovieLoadListener {
    void requestMovieDownload();
    void displayMovies(MovieOverviewResponse movieOverviewResponse);
    void displayError();
}
