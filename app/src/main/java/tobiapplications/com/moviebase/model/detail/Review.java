package tobiapplications.com.moviebase.model.detail;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tobias on 16.06.2017.
 */

public class Review {

    @SerializedName("id")
    private String id;
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;
    @SerializedName("url")
    private String url;

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
