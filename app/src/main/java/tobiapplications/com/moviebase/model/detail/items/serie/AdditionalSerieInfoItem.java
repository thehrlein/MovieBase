package tobiapplications.com.moviebase.model.detail.items.serie;

import java.util.ArrayList;

import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.detail.Genre;

/**
 * Created by Tobias on 17.09.2017.
 */

public class AdditionalSerieInfoItem implements DisplayableItem {

    private String originalTitle;
    private String episodes;
    private String seasons;
    private ArrayList<Genre> genres;
    private String homepage;

    public AdditionalSerieInfoItem(String originalTitle, String episodes, String seasons, ArrayList<Genre> genres, String homepage) {
        this.originalTitle = originalTitle;
        this.episodes = episodes;
        this.seasons = seasons;
        this.genres = genres;
        this.homepage = homepage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getEpisodes() {
        return episodes;
    }

    public String getSeasons() {
        return seasons;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }
}
