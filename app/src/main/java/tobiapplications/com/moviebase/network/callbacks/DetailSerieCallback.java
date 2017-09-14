package tobiapplications.com.moviebase.network.callbacks;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tobiapplications.com.moviebase.model.detail.SeriesDetailResponse;
import tobiapplications.com.moviebase.ui.detail.DetailActivityPresenter;

/**
 * Created by Tobias on 14.09.2017.
 */

public class DetailSerieCallback implements Callback<SeriesDetailResponse> {

    private DetailActivityPresenter presenter;

    public DetailSerieCallback(DetailActivityPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResponse(Call<SeriesDetailResponse> call, Response<SeriesDetailResponse> response) {
        if (response.isSuccessful()) {
            presenter.displaySeriesResponse(response.body());
        } else {
            presenter.displayError();
        }
    }

    @Override
    public void onFailure(Call<SeriesDetailResponse> call, Throwable t) {
        presenter.displayError();
    }
}
