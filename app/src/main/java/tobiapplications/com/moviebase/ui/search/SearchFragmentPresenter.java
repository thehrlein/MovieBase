package tobiapplications.com.moviebase.ui.search;

import android.content.Context;
import android.widget.Toast;

import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;
import tobiapplications.com.moviebase.network.DataManager;

/**
 * Created by Tobias on 16.06.2017.
 */

public class SearchFragmentPresenter implements SearchFragmentContract.Presenter {

    private SearchFragmentContract.View parent;
    private Context context;
    private String query;

    public SearchFragmentPresenter(SearchFragmentContract.View parent) {
        this.parent = parent;
    }

    @Override
    public void init(String query, Context context) {
        this.context = context;
        this.query = query;
        parent.setDownloadIsActive();
        parent.setAdapter();
        requestMovieDownload();
    }

    @Override
    public void requestMovieDownload() {
        DataManager.getInstance().requestSearchMovie(this, query);
    }

    @Override
    public void requestSeriesDownload() {

    }

    @Override
    public void displayMovies(MovieOverviewResponse movieOverviewResponse) {
        parent.setDownloadFinished();
        parent.setSearchMovies(movieOverviewResponse.getMovies());
    }

    @Override
    public void displayError(String message) {
        Toast.makeText(context, "Error " + message, Toast.LENGTH_SHORT).show();
    }
}
