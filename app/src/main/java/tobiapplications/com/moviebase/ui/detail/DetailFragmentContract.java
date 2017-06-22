package tobiapplications.com.moviebase.ui.detail;

import java.util.ArrayList;

import tobiapplications.com.moviebase.listener.OnOverviewMovieLoadListener;
import tobiapplications.com.moviebase.model.RecyclerItem;
import tobiapplications.com.moviebase.model.detail.ActorsResponse;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;
import tobiapplications.com.moviebase.model.detail.TrailersResponse;
import tobiapplications.com.moviebase.model.detail.YtSingleTrailerResponse;

/**
 * Created by Tobias on 13.06.2017.
 */

public interface DetailFragmentContract {

    interface View extends android.view.View.OnClickListener{

        void displayUiViews(ArrayList<RecyclerItem> detailItems);
        void displayUiView(RecyclerItem item);
    }

    interface Presenter extends OnOverviewMovieLoadListener {

        void buildUiFromResponse(MovieDetailResponse detailResponse);
        void requestReviews();
        void displayReviews(ReviewResponse response);
        void requestActors();
        void displayActors(ActorsResponse body);
        void displayTrailers(TrailersResponse body);
        void displaySingleYoutubeTrailer(YtSingleTrailerResponse body, String trailerKey);
    }
}
