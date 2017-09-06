package tobiapplications.com.moviebase.model.detail.items;

import java.util.ArrayList;

import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.general_items.MoviePosterItem;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;

/**
 * Created by Tobias on 15.06.2017.
 */

public class SimilarMoviesItem implements DisplayableItem {
    private ArrayList<MoviePosterItem> movies;

    public SimilarMoviesItem(ArrayList<MoviePosterItem> movies) {
        this.movies = movies;
    }

    public ArrayList<MoviePosterItem> getMovies() {
        return movies;
    }
}
