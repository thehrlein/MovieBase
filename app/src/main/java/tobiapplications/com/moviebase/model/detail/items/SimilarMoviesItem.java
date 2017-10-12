package tobiapplications.com.moviebase.model.detail.items;

import java.util.ArrayList;

import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.general_items.MoviePosterItem;

/**
 * Created by Tobias on 15.06.2017.
 */

public class SimilarMoviesItem implements DisplayableItem {
    private ArrayList<MoviePosterItem> movies;
    private String title;

    public SimilarMoviesItem(ArrayList<MoviePosterItem> movies, String title) {
        this.movies = movies;
        this.title = title;
    }

    public ArrayList<MoviePosterItem> getMovies() {
        return movies;
    }

    public String getTitle() {
        return title;
    }
}
