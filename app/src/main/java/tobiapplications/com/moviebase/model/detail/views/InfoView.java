
package tobiapplications.com.moviebase.model.detail.views;

/**
 * Created by Tobias on 15.06.2017.
 */

public class InfoView {

    private String imagePath;
    private double voteAverage;
    private int voteCount;

    public InfoView(String imagePath, double voteAverage, int count) {
        this.imagePath = imagePath;
        this.voteAverage = voteAverage;
        this.voteCount = count;
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
}
