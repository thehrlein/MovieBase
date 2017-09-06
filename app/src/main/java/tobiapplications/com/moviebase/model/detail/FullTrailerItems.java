package tobiapplications.com.moviebase.model.detail;

import java.util.ArrayList;

import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.detail.items.TrailerItem;

/**
 * Created by Tobias on 06.09.2017.
 */

public class FullTrailerItems implements DisplayableItem{

    private ArrayList<TrailerItem> trailerItems;

    public FullTrailerItems(ArrayList<TrailerItem> trailerItems) {
        this.trailerItems = trailerItems;
    }

    public ArrayList<TrailerItem> getTrailerItems() {
        return trailerItems;
    }

}
