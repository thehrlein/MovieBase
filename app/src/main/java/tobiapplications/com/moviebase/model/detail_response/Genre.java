package tobiapplications.com.moviebase.model.detail_response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tobias on 11.06.2017.
 */

public class Genre {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
