package tobiapplications.com.moviebase.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import tobiapplications.com.moviebase.model.detail_response.Collection;
import tobiapplications.com.moviebase.model.detail_response.Genre;
import tobiapplications.com.moviebase.model.detail_response.ProductionCompany;
import tobiapplications.com.moviebase.model.detail_response.ProductionCountry;
import tobiapplications.com.moviebase.model.detail_response.SpokenLanguage;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 11.06.2017.
 */

public class MovieDetailResponse implements Serializable {

    @SerializedName("adult")
    private boolean adult;
    @SerializedName("backdrop_path")
    private String backgroundImagePath;
    @SerializedName("belongs_to_collection")
    private Collection collection;
    @SerializedName("budget")
    private int budget;
    @SerializedName("genres")
    private ArrayList<Genre> genres;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("id")
    private int id;
    @SerializedName("imdb_id")
    private String imdbId;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("overview")
    private String description;
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("poster_path")
    private String titleImagePath;
    @SerializedName("production_companies")
    private ArrayList<ProductionCompany> productionCompanies;
    @SerializedName("production_countries")
    private ArrayList<ProductionCountry> productionCountries;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("revenue")
    private int revenue;
    @SerializedName("runtime")
    private int runtime;
    @SerializedName("spoken_languages")
    private ArrayList<SpokenLanguage> spokenLanguages;
    @SerializedName("status")
    private String status;
    @SerializedName("tagline")
    private String tagline;
    @SerializedName("title")
    private String title;
    @SerializedName("video")
    private boolean video;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("vote_count")
    private int voteCount;

    public boolean isAdult() {
        return adult;
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    public Collection getCollection() {
        return collection;
    }

    public int getBudget() {
        return budget;
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

    public String getImdbId() {
        return imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getDescription() {
        return description;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getTitleImagePath() {
        return titleImagePath;
    }

    public ArrayList<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public ArrayList<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public ArrayList<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }
}
