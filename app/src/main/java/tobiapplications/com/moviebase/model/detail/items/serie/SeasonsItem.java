package tobiapplications.com.moviebase.model.detail.items.serie;

import java.util.ArrayList;

import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.detail.Season;

/**
 * Created by Tobias on 20.09.2017.
 */

public class SeasonsItem implements DisplayableItem {

    private ArrayList<Season> seasons;

    public SeasonsItem(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }
}
