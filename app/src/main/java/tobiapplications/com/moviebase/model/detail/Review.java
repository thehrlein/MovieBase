package tobiapplications.com.moviebase.model.detail;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tobias on 16.06.2017.
 */

public class Review {

    @SerializedName("name")
    private String reviewer;
    @SerializedName("review")
    private String reviewText;

    public String getReviewer() {
        return reviewer;
    }

    public String getReviewText() {
        return reviewText;
    }
}
