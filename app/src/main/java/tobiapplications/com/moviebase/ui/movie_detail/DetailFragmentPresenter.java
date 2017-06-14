package tobiapplications.com.moviebase.ui.movie_detail;

import tobiapplications.com.moviebase.model.detail_response.MovieDetailResponse;

/**
 * Created by Tobias on 14.06.2017.
 */

public class DetailFragmentPresenter implements DetailFragmentContract.Presenter {

    private MovieDetailResponse detailMovie;

    public DetailFragmentPresenter() {

    }

    @Override
    public void buildUiFromResponse(MovieDetailResponse detailMovie) {
        this.detailMovie = detailMovie;



    }
}
