package tobiapplications.com.moviebase.model.detail.items;

import tobiapplications.com.moviebase.model.detail.YtThumbnailObject;
import tobiapplications.com.moviebase.model.detail.YtTrailerStatistic;

/**
 * Created by Tobias on 20.06.2017.
 */

public class TrailerItem {

    private String title;
    private YtThumbnailObject thumbnails;
    private YtTrailerStatistic statistics;

    public TrailerItem(String title, YtThumbnailObject thumbnails, YtTrailerStatistic statistics) {
        this.title = title;
        this.thumbnails = thumbnails;
        this.statistics = statistics;
    }

    public String getTitle() {
        return title;
    }

    public YtThumbnailObject getThumbnails() {
        return thumbnails;
    }

    public YtTrailerStatistic getStatistics() {
        return statistics;
    }
}
