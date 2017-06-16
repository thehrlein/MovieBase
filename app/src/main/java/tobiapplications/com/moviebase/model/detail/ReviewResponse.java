package tobiapplications.com.moviebase.model.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tobias on 16.06.2017.
 */

public class ReviewResponse implements Serializable {

    @SerializedName("count")
    private int count;
    @SerializedName("reviews")
    private ArrayList<Review> reviews;

    public int getCount() {
        return count;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }
}
