package tobiapplications.com.moviebase.model.search;

import tobiapplications.com.moviebase.model.DisplayableItem;

/**
 * Created by Tobias Hehrlein on 11.10.2017.
 */

public class SearchMovieItem implements DisplayableItem {

    private int id;
    private String title;
    private String imagePath;
    private String releaseDate;

    public SearchMovieItem(int id, String title, String imagePath, String releaseDate) {
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
