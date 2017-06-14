package tobiapplications.com.moviebase.ui.movie_detail;

import android.view.View;

import tobiapplications.com.moviebase.model.detail_response.MovieDetailResponse;

/**
 * Created by Tobias on 13.06.2017.
 */

public interface DetailFragmentContract {

    interface View extends android.view.View.OnClickListener{

    }

    interface Presenter {

        void buildUiFromResponse(MovieDetailResponse detailResponse);
    }
}
