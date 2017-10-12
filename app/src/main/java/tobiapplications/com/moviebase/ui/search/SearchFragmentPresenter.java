package tobiapplications.com.moviebase.ui.search;

import android.content.Context;

import java.util.ArrayList;

import timber.log.Timber;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.overview.PosterOverviewItem;
import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;
import tobiapplications.com.moviebase.model.search.SearchMovieItem;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 16.06.2017.
 */

public class SearchFragmentPresenter implements SearchFragmentContract.Presenter {

    private SearchFragmentContract.View parent;
    private Context context;
    private String query;
    private int overviewType;

    public SearchFragmentPresenter(SearchFragmentContract.View parent) {
        this.parent = parent;
    }

    @Override
    public void init(String query, int overviewType, Context context) {
        this.context = context;
        this.query = query;
        this.overviewType = overviewType;
        parent.setDownloadIsActive();
        parent.setAdapter();

        if (overviewType == Constants.Type.MOVIES) {
            requestMoviesDownload();
        } else if (overviewType == Constants.Type.SERIES) {
            requestSeriesDownload();
        }
    }

    @Override
    public void requestMoviesDownload() {
        DataManager.getInstance().requestSearchMovie(this, query);
    }

    @Override
    public void requestSeriesDownload() {
        DataManager.getInstance().requestSearchSerie(this, query);
    }

    @Override
    public void displayPosterItems(MovieOverviewResponse movieOverviewResponse) {
        parent.setDownloadFinished();
        ArrayList<DisplayableItem> searchMovieItems = getSearchMovieItems(movieOverviewResponse);
        parent.setSearchMovies(searchMovieItems);
    }

    private ArrayList<DisplayableItem> getSearchMovieItems(MovieOverviewResponse movieOverviewResponse) {
        ArrayList<DisplayableItem> searchMovieItems = new ArrayList<>();
        for (PosterOverviewItem model : movieOverviewResponse.getMovies()) {
            int id = model.getId();
            String title = getTitle(model);
            String imagePath = model.getTitleImagePath();
            String releaseDate = getReleaseDate(model);
            SearchMovieItem item = new SearchMovieItem(id, title, imagePath, releaseDate);
            searchMovieItems.add(item);
        }

        return searchMovieItems;
    }

    private String getReleaseDate(PosterOverviewItem model) {
        if (overviewType == Constants.Type.MOVIES) {
            return model.getReleaseDate();
        } else {
            return model.getFirstAirDate();
        }
    }

    private String getTitle(PosterOverviewItem model) {
        if (overviewType == Constants.Type.MOVIES) {
            return model.getTitle();
        } else {
            return model.getName();
        }
    }

    @Override
    public void displayError(String message) {
        Timber.d("Error: " + message);
    }
}
