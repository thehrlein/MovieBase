
package tobiapplications.com.moviebase.model.detail.items;

/**
 * Created by Tobias on 15.06.2017.
 */

public class InfoItem {

    private String imagePath;
    private double voteAverage;
    private int voteCount;
    private String releaseDate;
    private boolean adult;
    private int runtime;
    private String status;

    public InfoItem(String imagePath, double voteAverage, int count, String releaseDate, boolean adult, int runtime, String status) {
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
