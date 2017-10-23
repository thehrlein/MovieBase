package tobiapplications.com.moviebase.ui.overview;

import android.os.Bundle;

import java.util.ArrayList;

import tobiapplications.com.moviebase.databinding.FragmentOverviewBinding;
import tobiapplications.com.moviebase.databinding.FragmentOverviewTabBinding;
import tobiapplications.com.moviebase.listener.OnLoadMoreMoviesListener;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.listener.OnOverviewMovieLoadListener;
import tobiapplications.com.moviebase.model.overview.PosterOverviewItem;
import tobiapplications.com.moviebase.utils.mvp.BaseMvpPresenter;
import tobiapplications.com.moviebase.utils.mvp.BaseView;

/**
 * Created by Tobias Hehrlein on 14.10.2017.
 */

public interface BaseTabContract {

    interface Presenter extends BaseMvpPresenter<View>, OnOverviewMovieLoadListener {
        void parseArguments(Bundle arguments);
        void getTypeAndLoadItems(Bundle arguments);
        void load();
        boolean noMoviesShown();
        boolean hasInternetConnection();
        void requestDownload();
        void onMovieClick(int id);
        void loadMore();
    }

    interface View extends BaseView, OnMovieClickListener, OnLoadMoreMoviesListener {
        void setGridViewAndAdapter();
        void showNetworkError(boolean noNetwork);
        void setMovies(ArrayList<PosterOverviewItem> movies);
        void showLoading(boolean load);
        int getCurrentMovieSize();
        void insertLoadingItem();
        void loadMoreMovies();
        void onMovieClick(int id);
        void startDetailActivity(int id, int type);

        void setPresenter(BaseTabPresenter presenter);
    }

}
