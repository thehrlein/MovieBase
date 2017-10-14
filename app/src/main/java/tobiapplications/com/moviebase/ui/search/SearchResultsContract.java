package tobiapplications.com.moviebase.ui.search;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.listener.OnOverviewMovieLoadListener;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.search.SearchMovieItem;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 16.06.2017.
 */

public interface SearchResultsContract {

    interface View extends OnMovieClickListener {
        void setDownloadIsActive();
        void setDownloadFinished();
        void setAdapter();
        void setSearchMovies(ArrayList<DisplayableItem> movies);
        void setTitle(String title);
        void startDetailActivity(int id, int type);
    }

    interface Presenter extends OnOverviewMovieLoadListener {
        void init(Bundle arguments, Context context);
        void onResume();
        void onMovieClick(int id);
    }
}
