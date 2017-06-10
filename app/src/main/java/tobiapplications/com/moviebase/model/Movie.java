package tobiapplications.com.moviebase.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Tobias on 10.06.2017.
 */

public class Movie {

     @SerializedName("id")
     private String id;
     @SerializedName("title")
     private String title;
     @SerializedName("poster_path")
     private String titleImagePath;
     @SerializedName("backdrop_path")
     private String backgroundImagePath;
     @SerializedName("release_date")
     private String year;
     @SerializedName("vote_average")
     private String rating;
     @SerializedName("overview")
     private String description;
     @SerializedName("genre_ids")
     private ArrayList<Integer> genres;
     @SerializedName("adult")
     private Boolean adult;

    public String getTitle() {
        return title;
    }

    public String getTitleImagePath() {
        return titleImagePath;
    }

    public String getId() {
        return id;
    }

    public String getYear() {
        return year;
    }

    public String getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    public ArrayList<Integer> getGenres() {
        return genres;
    }

    public Boolean getAdult() {
        return adult;
    }
}
