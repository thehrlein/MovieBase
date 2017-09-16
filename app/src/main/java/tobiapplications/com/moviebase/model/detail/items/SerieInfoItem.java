package tobiapplications.com.moviebase.model.detail.items;

import tobiapplications.com.moviebase.model.DisplayableItem;

/**
 * Created by Tobias on 16.09.2017.
 */

public class SerieInfoItem implements DisplayableItem {

    private double voteAverage;
    private int voteCount;
    private String firstAirDate;
    private String lastAirDate;
    private boolean adult;
    private String status;

    public SerieInfoItem(double voteAverage, int voteCount, String firstAirDate, String lastAirDate, boolean adult, String status) {
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.firstAirDate = firstAirDate;
        this.lastAirDate = lastAirDate;
        this.adult = adult;
        this.status = status;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getStatus() {
        return status;
    }
}
