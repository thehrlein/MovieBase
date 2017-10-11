package tobiapplications.com.moviebase.ui.search;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
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
    private Constants.OverviewType overviewType;

    public SearchFragmentPresenter(SearchFragmentContract.View parent) {
        this.parent = parent;
    }

    @Override
    public void init(String query, Constants.OverviewType overviewType, Context context) {
        this.context = context;
        this.query = query;
        this.overviewType = overviewType;
        parent.setDownloadIsActive();
        parent.setAdapter();

        if (overviewType == Constants.OverviewType.MOVIES) {
            requestMoviesDownload();
        } else if (overviewType == Constants.OverviewType.SERIES) {
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
    public void displayMovies(MovieOverviewResponse movieOverviewResponse) {
        parent.setDownloadFinished();
        ArrayList<DisplayableItem> searchMovieItems = getSearchMovieItems(movieOverviewResponse);
        parent.setSearchMovies(searchMovieItems);
    }

    private ArrayList<DisplayableItem> getSearchMovieItems(MovieOverviewResponse movieOverviewResponse) {
        ArrayList<DisplayableItem> searchMovieItems = new ArrayList<>();
        for (MovieOverviewModel model : movieOverviewResponse.getMovies()) {
            int id = model.getId();
            String title = getTitle(model);
            String imagePath = model.getTitleImagePath();
            String releaseDate = getReleaseDate(model);
            SearchMovieItem item = new SearchMovieItem(id, title, imagePath, releaseDate);
            searchMovieItems.add(item);
        }

        return searchMovieItems;
    }

    private String getReleaseDate(MovieOverviewModel model) {
        if (overviewType == Constants.OverviewType.MOVIES) {
            return model.getReleaseDate();
        } else {
            return model.getFirstAirDate();
        }
    }

    private String getTitle(MovieOverviewModel model) {
        if (overviewType == Constants.OverviewType.MOVIES) {
            return model.getTitle();
        } else {
            return model.getName();
        }
    }

    @Override
    public void displayError(String message) {
        Toast.makeText(context, "Error " + message, Toast.LENGTH_SHORT).show();
    }
}
