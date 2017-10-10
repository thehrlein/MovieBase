package tobiapplications.com.moviebase.model.overview;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.utils.SQLUtils;

/**
 * Created by Tobias on 10.06.2017.
 */

public class MovieOverviewModel implements DisplayableItem {

     @SerializedName("id")
     private int id;
     @SerializedName("title")
     private String title;
     @SerializedName("poster_path")
     private String titleImagePath;
     @SerializedName("backdrop_path")
     private String backgroundImagePath;
     @SerializedName("release_date")
     private String releaseDate;
     @SerializedName("vote_average")
     private String rating;
     @SerializedName("overview")
     private String description;
     @SerializedName("genre_ids")
     private ArrayList<Integer> genres;
     @SerializedName("adult")
     private Boolean adult;

    // name instead of title for series
    @SerializedName("name")
    private String name;

    public MovieOverviewModel(int id, String title, String titleImagePath, String backgroundImagePath, String releaseDate, String rating, String description, ArrayList<Integer> genres, Boolean adult) {
        this.id = id;
        this.title = title;
        this.titleImagePath = titleImagePath;
        this.backgroundImagePath = backgroundImagePath;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.description = description;
        this.genres = genres;
        this.adult = adult;
    }

    public MovieOverviewModel(int id, String title, String titleImagePath) {
        this.id = id;
        this.title = title;
        this.titleImagePath = titleImagePath;
    }

    public MovieOverviewModel(Cursor data) {
        this.id = data.getInt(SQLUtils.INDEX_COLUMN_ID);
        this.title = data.getString(SQLUtils.INDEX_COLUMN_TITLE);
        this.titleImagePath = data.getString(SQLUtils.INDEX_COLUMN_TITLE_IMAGE_PATH);
    }

    public String getTitle() {
        return title;
    }

    public String getTitleImagePath() {
        return titleImagePath;
    }

    public int getId() {
        return id;
    }

    public String getReleaseDate() {
        return releaseDate;
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

    public String getName() {
        return name;
    }
}
