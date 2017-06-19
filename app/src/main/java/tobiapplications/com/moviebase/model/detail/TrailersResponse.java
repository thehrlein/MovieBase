package tobiapplications.com.moviebase.model.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tobias on 19.06.2017.
 */

public class TrailersResponse implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private ArrayList<Trailer> trailers;

    public int getId() {
        return id;
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }
}
