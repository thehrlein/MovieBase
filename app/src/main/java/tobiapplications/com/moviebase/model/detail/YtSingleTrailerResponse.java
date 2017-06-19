package tobiapplications.com.moviebase.model.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Tobias on 19.06.2017.
 */

public class YtSingleTrailerResponse implements Serializable {

    @SerializedName("items")
    private YtTrailerItem[] item;

    public YtTrailerItem getItem() {
        return item[0];
    }

    private class YtTrailerItem implements Serializable {

        @SerializedName("snippet/title")
        private YtSnippet snippet;

        public YtSnippet getSnippet() {
            return snippet;
        }

        private class YtSnippet implements Serializable {

            @SerializedName("title")
            private String title;
            @SerializedName("thumbnails")
            private YtThumbnailObject thumbnailObject;
            @SerializedName("statistics")
            private YtTrailerStatistic trailerStatistic;

            public String getTitle() {
                return title;
            }

            public YtThumbnailObject getThumbnailObject() {
                return thumbnailObject;
            }

            public YtTrailerStatistic getTrailerStatistic() {
                return trailerStatistic;
            }
        }
    }
}
