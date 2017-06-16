package tobiapplications.com.moviebase.ui.search;

import java.util.ArrayList;

import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.listener.OnOverviewMovieLoad;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;

/**
 * Created by Tobias on 16.06.2017.
 */

public interface SearchContract {

    interface View extends OnMovieClickListener {
        void setDownloadIsActive();
        void setDownloadFinished();
        void setAdapter();
        void setSearchMovies(ArrayList<MovieOverviewModel> movies);
    }

    interface Presenter extends OnOverviewMovieLoad {

        void init(String query);
    }
}
