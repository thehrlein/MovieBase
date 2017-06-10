package tobiapplications.com.moviebase.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tobi-Laptop on 20.01.2017.
 */

public class MovieResponse implements Serializable {

    @SerializedName("page")
    private int currentPage;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private ArrayList<Movie> movies;


    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
