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
        if (itemNullOrEmpty()) {
            return null;
        }
        return item[0];
    }

    private boolean itemNullOrEmpty() {
        if (item == null || item.length == 0) {
            return true;
        }
        return false;
    }

    private boolean snippetNullOrEmpty() {
        if (itemNullOrEmpty()) {
            return true;
        }
        YtTrailerItem.YtSnippet snippet = getItem().getSnippet();
        if (snippet == null) {
            return true;
        }
        return false;
    }

    public YtThumbnailObject getThumbnails() {
        if (snippetNullOrEmpty()) {
            return null;
        }
        return getItem().getSnippet().getThumbnailObject();
    }

    public YtTrailerStatistic getStatistics() {
        if (itemNullOrEmpty()) {
            return null;
        }
        return getItem().getTrailerStatistic();
    }

    public String getTitle() {
        if (snippetNullOrEmpty()) {
            return "";
        }
        return getItem().getSnippet().getTitle();
    }

    private class YtTrailerItem implements Serializable {

        @SerializedName("snippet")
        private YtSnippet snippet;
        @SerializedName("statistics")
        private YtTrailerStatistic trailerStatistic;

        public YtSnippet getSnippet() {
            return snippet;
        }

        public YtTrailerStatistic getTrailerStatistic() {
            return trailerStatistic;
        }

        private class YtSnippet implements Serializable {

            @SerializedName("title")
            private String title;
            @SerializedName("thumbnails")
            private YtThumbnailObject thumbnailObject;

            public String getTitle() {
                return title;
            }

            public YtThumbnailObject getThumbnailObject() {
                return thumbnailObject;
            }


        }
    }
}
