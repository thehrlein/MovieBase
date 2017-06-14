package tobiapplications.com.moviebase.ui.detail;

import java.util.ArrayList;

import tobiapplications.com.moviebase.model.RecyclerItem;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;

/**
 * Created by Tobias on 13.06.2017.
 */

public interface DetailFragmentContract {

    interface View extends android.view.View.OnClickListener{

        void displayUiViews(ArrayList<RecyclerItem> detailItems);
    }

    interface Presenter {

        void buildUiFromResponse(MovieDetailResponse detailResponse);
    }
}
