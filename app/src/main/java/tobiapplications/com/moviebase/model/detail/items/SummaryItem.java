package tobiapplications.com.moviebase.model.detail.items;

import tobiapplications.com.moviebase.model.DisplayableItem;

/**
 * Created by Tobias on 15.06.2017.
 */

public class SummaryItem implements DisplayableItem {

    private String summary;

    public SummaryItem(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }
}
