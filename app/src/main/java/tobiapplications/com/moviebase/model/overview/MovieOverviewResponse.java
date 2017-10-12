package tobiapplications.com.moviebase.model.overview;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import tobiapplications.com.moviebase.model.general_items.MoviePosterItem;

/**
 * Created by Tobi-Laptop on 20.01.2017.
 */

public class MovieOverviewResponse implements Serializable {

    @SerializedName("page")
    private int currentPage;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private ArrayList<PosterOverviewItem> movies;


    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public ArrayList<PosterOverviewItem> getMovies() {
        return movies;
    }

    public ArrayList<MoviePosterItem> getMoviePosterItems() {
        ArrayList<MoviePosterItem> posterItems = new ArrayList<>();

        for (PosterOverviewItem model : movies) {
            int id = model.getId();
            String imagePath = model.getTitleImagePath();
            String imageTitle = model.getTitle();
            MoviePosterItem item = new MoviePosterItem(id, imagePath, imageTitle);
            posterItems.add(item);
        }

        return posterItems;
    }

    public ArrayList<MoviePosterItem> getSeriePosterItems() {
        ArrayList<MoviePosterItem> posterItems = new ArrayList<>();

        for (PosterOverviewItem model : movies) {
            int id = model.getId();
            String imagePath = model.getTitleImagePath();
            String imageTitle = model.getName();
            MoviePosterItem item = new MoviePosterItem(id, imagePath, imageTitle);
            posterItems.add(item);
        }

        return posterItems;
    }
}
