package tobiapplications.com.moviebase.model.detail_response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tobias on 11.06.2017.
 */

public class ProductionCountry {

    @SerializedName("iso_3166_1")
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
