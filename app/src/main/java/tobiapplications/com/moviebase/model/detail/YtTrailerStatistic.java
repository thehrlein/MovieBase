package tobiapplications.com.moviebase.model.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Tobias on 19.06.2017.
 */

public class YtTrailerStatistic implements Serializable {

    @SerializedName("viewCount")
    private String viewCount;
    @SerializedName("likeCount")
    private String likeCount;
    @SerializedName("dislikeCount")
    private String dislikeCount;

    public String getViewCount() {
        return viewCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public String getDislikeCount() {
        return dislikeCount;
    }
}
