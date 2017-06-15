package tobiapplications.com.moviebase.model.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Tobias on 11.06.2017.
 */

public class Collection implements Serializable{

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("poster_path")
    private String titleImagePath;
    @SerializedName("backdrop_path")
    private String backgroundImagePath;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitleImagePath() {
        return titleImagePath;
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }
}
