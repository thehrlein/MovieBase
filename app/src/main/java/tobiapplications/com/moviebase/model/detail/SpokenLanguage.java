package tobiapplications.com.moviebase.model.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Tobias on 11.06.2017.
 */

public class SpokenLanguage implements Serializable {

    @SerializedName("iso_639_1")
    private String isoCode;
    @SerializedName("name")
    private String name;

    public String getIsoCode() {
        return isoCode;
    }

    public String getName() {
        return name;
    }
}
