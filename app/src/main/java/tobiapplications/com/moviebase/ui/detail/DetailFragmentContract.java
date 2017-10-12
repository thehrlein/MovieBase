package tobiapplications.com.moviebase.ui.detail;

import java.util.ArrayList;

import tobiapplications.com.moviebase.listener.OnOverviewMovieLoadListener;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.detail.ActorsResponse;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;
import tobiapplications.com.moviebase.model.detail.SeriesDetailResponse;
import tobiapplications.com.moviebase.model.detail.TrailersResponse;
import tobiapplications.com.moviebase.model.detail.YtSingleTrailerResponse;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 13.06.2017.
 */

public interface DetailFragmentContract {

    interface View {

        void displayUiViews(ArrayList<DisplayableItem> detailItems);
        void displayUiView(DisplayableItem item);
    }

    interface Presenter extends OnOverviewMovieLoadListener {
        void requestReviews();
        void displayReviews(ReviewResponse response);
        void requestActors();
        void displayActors(ActorsResponse body);
        void displayTrailers(TrailersResponse body);
        void displaySingleYoutubeTrailer(YtSingleTrailerResponse body, String trailerKey);
        void init(Constants.OverviewType overviewType, MovieDetailResponse detailMovie, SeriesDetailResponse detailSerie);
    }
}
