package tobiapplications.com.moviebase.ui.movie_detail;

import android.widget.Toast;

import com.stfalcon.frescoimageviewer.ImageViewer;

import tobiapplications.com.moviebase.model.MovieDetailResponse;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.utils.MovieDetailUtils;

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
        MovieDetailUtils.setMovieDetailResponse(detailResponse);
        clickedMovie = detailResponse;
        parent.setMovieInformation(clickedMovie.getTitle(), clickedMovie.getBackgroundImagePath());
    }

    public void displayError() {
        Toast.makeText(parent, "Error MovieDetailPresenter", Toast.LENGTH_SHORT).show();
    }

    public void openToolbarImage() {
        if (clickedMovie != null){
            new ImageViewer.Builder(parent, new String[]{clickedMovie.getBackgroundImagePath()})
                    .setStartPosition(0)
                    .show();
        }

    }
}
