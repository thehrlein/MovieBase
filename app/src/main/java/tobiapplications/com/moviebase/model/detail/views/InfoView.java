
package tobiapplications.com.moviebase.model.detail.views;

import android.widget.TextView;

/**
 * Created by Tobias on 15.06.2017.
 */

public class InfoView {

    private String imagePath;
    private double voteAverage;
    private int voteCount;
    private String releaseDate;
    private boolean adult;
    private int runtime;

    public InfoView(String imagePath, double voteAverage, int count, String releaseDate, boolean adult, int runtime) {
        this.imagePath = imagePath;
        this.voteAverage = voteAverage;
        this.voteCount = count;
        this.releaseDate = releaseDate;
        this.adult = adult;
        this.runtime = runtime;
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
}
