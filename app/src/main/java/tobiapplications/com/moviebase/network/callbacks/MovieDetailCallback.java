package tobiapplications.com.moviebase.network.callbacks;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tobiapplications.com.moviebase.model.detail_response.MovieDetailResponse;
import tobiapplications.com.moviebase.ui.movie_detail.MovieDetailPresenter;

/**
 * Created by Tobias on 11.06.2017.
 */

public class MovieDetailCallback implements Callback<MovieDetailResponse> {

    private MovieDetailPresenter presenter;

    public MovieDetailCallback(MovieDetailPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
        if (response.isSuccessful()) {
            presenter.displayMovieResponse(response.body());
        }
    }

    @Override
    public void onFailure(Call<MovieDetailResponse> call, Throwable t) {
        presenter.displayError();
    }
}
