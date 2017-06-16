package tobiapplications.com.moviebase.model.detail.views;

import java.util.ArrayList;

import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;

/**
 * Created by Tobias on 15.06.2017.
 */

public class SimilarMoviesView {
    private ArrayList<MovieOverviewModel> movies;

    public SimilarMoviesView(ArrayList<MovieOverviewModel> movies) {
        this.movies = movies;
    }

    public ArrayList<MovieOverviewModel> getMovies() {
        return movies;
    }
}
