package tobiapplications.com.moviebase.network.callbacks;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tobiapplications.com.moviebase.model.detail.TrailersResponse;
import tobiapplications.com.moviebase.ui.detail.DetailFragmentContract;

/**
 * Created by Tobias on 19.06.2017.
 */

public class TrailersCallback implements Callback<TrailersResponse> {

    private DetailFragmentContract.Presenter presenter;

    public TrailersCallback(DetailFragmentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResponse(Call<TrailersResponse> call, Response<TrailersResponse> response) {
        if (response.isSuccessful()) {
            presenter.displayTrailers(response.body());
        } else {
            presenter.displayError("TrailersCallback " + response.message());
        }
    }

    @Override
    public void onFailure(Call<TrailersResponse> call, Throwable t) {
        presenter.displayError("TrailersCallback ");
    }
}
