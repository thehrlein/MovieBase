package tobiapplications.com.moviebase.model;

/**
 * Created by Tobias on 11.06.2017.
 */

public class RecyclerItem {
    private int itemType;
    private Object item;

    public RecyclerItem(int itemType, Object item) {
        this.itemType = itemType;
        this.item = item;
    }

    public int getItemType() {
        return itemType;
    }

    public Object getItem() {
        return item;
    }
}
