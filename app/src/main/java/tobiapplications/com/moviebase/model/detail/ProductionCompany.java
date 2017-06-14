package tobiapplications.com.moviebase.model.detail;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tobias on 11.06.2017.
 */

public class ProductionCompany {

    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private int id;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
