package tobiapplications.com.moviebase.ui.search;

import android.content.Context;
import android.widget.Toast;

import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;
import tobiapplications.com.moviebase.network.DataManager;

/**
 * Created by Tobias on 16.06.2017.
 */

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View parent;
    private Context context;
    private String query;

    public SearchPresenter(SearchContract.View parent, Context context) {
        this.parent = parent;
        this.context = context;
    }

    @Override
    public void init(String query) {
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
    public void displayMovies(MovieOverviewResponse movieOverviewResponse) {
        parent.setDownloadFinished();
        parent.setSearchMovies(movieOverviewResponse.getMovies());
    }

    @Override
    public void displayError() {
        Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
    }
}
