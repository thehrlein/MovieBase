package tobiapplications.com.moviebase.model.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Tobias on 16.09.2017.
 */

public class Season implements Serializable {

    @SerializedName("air_date")
    private String airDate;
    @SerializedName("episode_count")
    private int epsisodeCount;
    @SerializedName("id")
    private int id;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("season_number")
    private int seasonNumber;

    public String getAirDate() {
        return airDate;
    }

    public String getEpsisodeCount() {
        return String.valueOf(epsisodeCount);
    }

    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getSeasonNumber() {
        return String.valueOf(seasonNumber);
    }
}
