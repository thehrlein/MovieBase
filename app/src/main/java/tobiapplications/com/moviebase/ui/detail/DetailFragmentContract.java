package tobiapplications.com.moviebase.ui.detail;

import java.util.ArrayList;

import tobiapplications.com.moviebase.listener.OnOverviewMovieLoad;
import tobiapplications.com.moviebase.model.RecyclerItem;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;

/**
 * Created by Tobias on 13.06.2017.
 */

public interface DetailFragmentContract {

    interface View extends android.view.View.OnClickListener{

        void displayUiViews(ArrayList<RecyclerItem> detailItems);
        void displayUiView(RecyclerItem item);
    }

    interface Presenter extends OnOverviewMovieLoad {

        void buildUiFromResponse(MovieDetailResponse detailResponse);
        void requestReviews();
        void displayReviews(ReviewResponse response);
    }
}
