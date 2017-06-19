package tobiapplications.com.moviebase.model.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Tobias on 19.06.2017.
 */

public class YtThumbnailObject implements Serializable {

    @SerializedName("default")
    private YtThumbnailContent defaultThumb;
    @SerializedName("medium")
    private YtThumbnailContent mediumThumb;
    @SerializedName("high")
    private YtThumbnailContent highThumb;
    @SerializedName("standard")
    private YtThumbnailContent standardThumb;
    @SerializedName("maxres")
    private YtThumbnailContent maxresThumb;

    public YtThumbnailContent getDefaultThumb() {
        return defaultThumb;
    }

    public YtThumbnailContent getMediumThumb() {
        return mediumThumb;
    }

    public YtThumbnailContent getHighThumb() {
        return highThumb;
    }

    public YtThumbnailContent getStandardThumb() {
        return standardThumb;
    }

    public YtThumbnailContent getMaxresThumb() {
        return maxresThumb;
    }

    private class YtThumbnailContent implements Serializable {

        @SerializedName("url")
        private String url;
        @SerializedName("width")
        private int width;
        @SerializedName("height")
        private int heigth;

        public String getUrl() {
            return url;
        }

        public int getWidth() {
            return width;
        }

        public int getHeigth() {
            return heigth;
        }
    }
}
