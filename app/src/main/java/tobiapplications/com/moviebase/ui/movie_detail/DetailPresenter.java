package tobiapplications.com.moviebase.ui.movie_detail;

import tobiapplications.com.moviebase.model.detail_response.MovieDetailResponse;

/**
 * Created by Tobias on 14.06.2017.
 */

public class DetailPresenter implements DetailFragmentContract.Presenter {

    private MovieDetailResponse detailMovie;

    public DetailPresenter(MovieDetailResponse detailMovie) {
        this.detailMovie = detailMovie;
    }
}
