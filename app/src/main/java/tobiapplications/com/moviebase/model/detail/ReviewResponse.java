package tobiapplications.com.moviebase.model.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import tobiapplications.com.moviebase.model.DisplayableItem;

/**
 * Created by Tobias on 16.06.2017.
 */

public class ReviewResponse implements Serializable, DisplayableItem {

    @SerializedName("id")
    private int id;
    @SerializedName("page")
    private int currentpage;
    @SerializedName("results")
    private ArrayList<Review> reviews;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public int getCurrentpage() {
        return currentpage;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public int getId() {
        return id;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
