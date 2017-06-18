package tobiapplications.com.moviebase.model.general_items;

import java.util.ArrayList;

/**
 * Created by Tobias on 18.06.2017.
 */

public class MoviePosterItem {

    private int id;
    private String imagePath;
    private String title;

    public MoviePosterItem(int id, String imagePath, String title) {
        this.id = id;
        this.imagePath = imagePath;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getTitle() {
        return title;
    }
}
