package tobiapplications.com.moviebase.network.callbacks;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tobiapplications.com.moviebase.model.detail.TrailerResponse;
import tobiapplications.com.moviebase.ui.detail.DetailFragmentContract;

/**
 * Created by Tobias on 19.06.2017.
 */

public class TrailersCallback implements Callback<TrailerResponse> {

    private DetailFragmentContract.Presenter presenter;

    public TrailersCallback(DetailFragmentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
        if (response.isSuccessful()) {
            presenter.displayTrailers(response.body());
        } else {
            presenter.displayError();
        }
    }

    @Override
    public void onFailure(Call<TrailerResponse> call, Throwable t) {
        presenter.displayError();
    }
}
