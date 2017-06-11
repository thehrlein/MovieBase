package tobiapplications.com.moviebase.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 10.06.2017.
 */

public class MovieOverviewModel {

     @SerializedName("id")
     private int id;
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

    public MovieOverviewModel(int id, String title, String titleImagePath, String backgroundImagePath, String year, String rating, String description, ArrayList<Integer> genres, Boolean adult) {
        this.id = id;
        this.title = title;
        this.titleImagePath = titleImagePath;
        this.backgroundImagePath = backgroundImagePath;
        this.year = year;
        this.rating = rating;
        this.description = description;
        this.genres = genres;
        this.adult = adult;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleImagePath() {
        return NetworkUtils.getImageBaseUrlLow() + titleImagePath + NetworkUtils.appendApiKey();
    }

    public int getId() {
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
