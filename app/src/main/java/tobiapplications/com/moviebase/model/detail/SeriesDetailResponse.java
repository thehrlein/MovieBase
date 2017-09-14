package tobiapplications.com.moviebase.model.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tobias on 14.09.2017.
 */

public class SeriesDetailResponse implements Serializable {

    @SerializedName("backdrop_path")
    private String backgroundImage;
    @SerializedName("created_by")
    private ArrayList<Creator> creator;
    @SerializedName("episode_run_time")
    private ArrayList<Integer> episodeRuntime;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("genres")
    private ArrayList<Genre> genres;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("id")
    private int id;
    @SerializedName("last_air_time")
    private String lastAirTime;
    @SerializedName("number_of_episodes")
    private int numberOfEpisodes;
    @SerializedName("number_of_seasons")
    private int numberOfSeasons;
    @SerializedName("original_name")
    private String originalName;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;

    public String getBackgroundImagePath() {
        return backgroundImage;
    }

    public ArrayList<Creator> getCreator() {
        return creator;
    }

    public ArrayList<Integer> getEpisodeRuntime() {
        return episodeRuntime;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getId() {
        return id;
    }

    public String getLastAirTime() {
        return lastAirTime;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getName() {
        return name;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getDescription() {
        return description;
    }
}
