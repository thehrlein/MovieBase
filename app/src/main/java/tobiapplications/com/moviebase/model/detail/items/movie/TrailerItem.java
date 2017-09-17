package tobiapplications.com.moviebase.model.detail.items.movie;

import tobiapplications.com.moviebase.model.detail.YtThumbnailObject;
import tobiapplications.com.moviebase.model.detail.YtTrailerStatistic;

/**
 * Created by Tobias on 20.06.2017.
 */

public class TrailerItem {

    private String title;
    private String key;
    private YtThumbnailObject thumbnails;
    private YtTrailerStatistic statistics;

    public TrailerItem(String title, String key, YtThumbnailObject thumbnails, YtTrailerStatistic statistics) {
        this.title = title;
        this.key = key;
        this.thumbnails = thumbnails;
        this.statistics = statistics;
    }

    public String getTitle() {
        return title;
    }

    public String getKey() {
        return key;
    }

    public YtThumbnailObject getThumbnails() {
        return thumbnails;
    }

    public YtTrailerStatistic getStatistics() {
        return statistics;
    }
}
