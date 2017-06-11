package tobiapplications.com.moviebase.ui.movie_detail;

import com.stfalcon.frescoimageviewer.ImageViewer;

import tobiapplications.com.moviebase.model.MovieDetailResponse;
import tobiapplications.com.moviebase.network.DataManager;

/**
 * Created by Tobias on 11.06.2017.
 */

public class MovieDetailPresenter {

    private MovieDetailActivity parent;
    private int movieId;
    private MovieDetailResponse clickedMovie;

    public MovieDetailPresenter(MovieDetailActivity activity, int movieId) {
        this.parent = activity;
        this.movieId = movieId;
    }

    public void requestSingleMovieDownload() {
        DataManager.getInstance().requestSingleMovie(this, movieId);
    }

    public void displayMovieResponse(MovieDetailResponse detailResponse) {
        clickedMovie = detailResponse;
        parent.setMovieInformation(clickedMovie.getTitle(), clickedMovie.getBackgroundImagePath());
    }

    public void displayError() {

    }

    public void openToolbarImage() {
        new ImageViewer.Builder(parent, new String[]{clickedMovie.getBackgroundImagePath()})
                .setStartPosition(0)
                .show();
    }
}
