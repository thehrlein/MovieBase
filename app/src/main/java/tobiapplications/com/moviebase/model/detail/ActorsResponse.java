package tobiapplications.com.moviebase.model.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import tobiapplications.com.moviebase.model.DisplayableItem;

/**
 * Created by Tobias on 17.06.2017.
 */

public class ActorsResponse implements Serializable, DisplayableItem {

    @SerializedName("id")
    private int id;
    @SerializedName("cast")
    private ArrayList<Actor> actors;

    public int getId() {
        return id;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }
}
