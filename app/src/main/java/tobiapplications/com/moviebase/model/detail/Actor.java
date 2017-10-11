package tobiapplications.com.moviebase.model.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Tobias on 17.06.2017.
 */

public class Actor implements Serializable {


    @SerializedName("cast_id")
    private int castId;
    @SerializedName("character")
    private String character;
    @SerializedName("credit_id")
    private String creditId;
    @SerializedName("gender")
    private int gender;
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("order")
    private int order;
    @SerializedName("profile_path")
    private String profilePath;

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public int getCastId() {
        return castId;
    }

    public String getCreditId() {
        return creditId;
    }

    public Gender getGender() {
        return new Gender(gender);
    }

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }
}
