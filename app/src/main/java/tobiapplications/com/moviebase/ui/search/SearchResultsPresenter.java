package tobiapplications.com.moviebase.ui.search;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

import timber.log.Timber;
import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.overview.PosterOverviewItem;
import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;
import tobiapplications.com.moviebase.model.search.SearchMovieItem;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.mvp.BasePresenter;

import static tobiapplications.com.moviebase.utils.GeneralUtils.*;

/**
 * Created by Tobias on 16.06.2017.
 */

public class SearchResultsPresenter extends BasePresenter<SearchResultsContract.View> implements SearchResultsContract.Presenter {

    private SearchResultsContract.View parent;
    private Context context;
    private String query;
    private int type;

    public SearchResultsPresenter(SearchResultsContract.View parent) {
        this.parent = parent;
    }

    @Override
    public void init(Bundle arguments, Context context) {
        this.context = context;
        parseArguments(arguments);
        parent.setDownloadIsActive();
        parent.setAdapter();

        if (isMovie(type)) {
            requestMoviesDownload();
        } else if (isSerie(type)) {
            requestSeriesDownload();
        }
    }

    private void parseArguments(Bundle arguments) {
        if (arguments == null) {
            return;
        }

        if (arguments.containsKey(Constants.SEARCH_QUERY)) {
            query = arguments.getString(Constants.SEARCH_QUERY);
        }
        if (arguments.containsKey(Constants.TYPE)) {
            type = arguments.getInt(Constants.TYPE);
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
        if (isMovie(type)) {
            return model.getReleaseDate();
        } else {
            return model.getFirstAirDate();
        }
    }

    private String getTitle(PosterOverviewItem model) {
        if (isMovie(type)) {
            return model.getTitle();
        } else {
            return model.getName();
        }
    }

    @Override
    public void displayError(String message) {
        Timber.d("Error: " + message);
    }

    @Override
    public void onResume() {
        parent.setTitle(getTitle());
    }

    private String getTitle() {
        String title;
        String appending = ": " + query;
        if (isMovie(type)) {
            title = context.getString(R.string.movie_title);
        } else {
            title = context.getString(R.string.series_title);
        }

        title += appending;
        return title;
    }

    @Override
    public void onMovieClick(int id) {
        parent.startDetailActivity(id, type);
    }
}
