package tobiapplications.com.moviebase.model.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tobias on 14.09.2017.
 */

public class SeriesDetailResponse implements Serializable {

    @SerializedName("adult")
    private boolean adult;
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
    @SerializedName("last_air_date")
    private String lastAirTime;
    @SerializedName("number_of_episodes")
    private String numberOfEpisodes;
    @SerializedName("number_of_seasons")
    private String numberOfSeasons;
    @SerializedName("original_name")
    private String originalName;
    @SerializedName("name")
    private String name;
    @SerializedName("overview")
    private String description;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("popularity")
    private Double popularity;
    @SerializedName("production_companies")
    private ArrayList<ProductionCompany> productionCompanies;
    @SerializedName("seasons")
    private ArrayList<Season> seasons;
    @SerializedName("status")
    private String status;
    @SerializedName("type")
    private String type;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("vote_count")
    private int voteCount;

    public boolean isAdult() {
        return adult;
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

    public String getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public String getNumberOfSeasons() {
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

    public Double getPopularity() {
        return popularity;
    }

    public ArrayList<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
