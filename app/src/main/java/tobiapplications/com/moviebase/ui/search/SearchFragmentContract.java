package tobiapplications.com.moviebase.ui.search;

import android.content.Context;

import java.util.ArrayList;

import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.listener.OnOverviewMovieLoadListener;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.search.SearchMovieItem;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 16.06.2017.
 */

public interface SearchFragmentContract {

    interface View extends OnMovieClickListener {
        void setDownloadIsActive();
        void setDownloadFinished();
        void setAdapter();
        void setSearchMovies(ArrayList<DisplayableItem> movies);
    }

    interface Presenter extends OnOverviewMovieLoadListener {
        void init(String query, int overviewType, Context context);
    }
}
