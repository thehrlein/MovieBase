package tobiapplications.com.moviebase.network.callbacks;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.ui.detail.DetailActivityPresenter;

/**
 * Created by Tobias on 11.06.2017.
 */

public class DetailCallback implements Callback<MovieDetailResponse> {

    private DetailActivityPresenter presenter;

    public DetailCallback(DetailActivityPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
        if (response.isSuccessful()) {
            presenter.displayMovieResponse(response.body());
        } else {
            presenter.displayError();
        }
    }

    @Override
    public void onFailure(Call<MovieDetailResponse> call, Throwable t) {
        presenter.displayError();
    }
}
