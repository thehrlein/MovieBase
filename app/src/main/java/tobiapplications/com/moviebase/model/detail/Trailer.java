package tobiapplications.com.moviebase.model.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Tobias on 19.06.2017.
 */

public class Trailer implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("iso_639_1")
    private String isoLangCode;
    @SerializedName("iso_3166_1")
    private String isoLandCode;
    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;
    @SerializedName("site")
    private String site;
    @SerializedName("size")
    private int size;
    @SerializedName("type")
    private String type;

    public String getId() {
        return id;
    }

    public String getIsoLangCode() {
        return isoLangCode;
    }

    public String getIsoLandCode() {
        return isoLandCode;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
}
