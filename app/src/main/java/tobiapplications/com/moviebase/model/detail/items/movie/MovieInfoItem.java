
package tobiapplications.com.moviebase.model.detail.items.movie;

import java.util.LinkedHashMap;

import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.utils.GeneralUtils;

/**
 * Created by Tobias on 15.06.2017.
 */

public class MovieInfoItem implements DisplayableItem{

    private String imagePath;
    private double voteAverage;
    private int voteCount;
    private String releaseDate;
    private boolean adult;
    private int runtime;
    private String status;

    public MovieInfoItem(String imagePath, double voteAverage, int count, String releaseDate, boolean adult, int runtime, String status) {
        this.imagePath = imagePath;
        this.voteAverage = voteAverage;
        this.voteCount = count;
        this.releaseDate = releaseDate;
        this.adult = adult;
        this.runtime = runtime;
        this.status = status;
    }

    public String getImagePath() {
        return imagePath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public boolean isAdult() {
        return adult;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getStatus() {
       return status;
    }
}
