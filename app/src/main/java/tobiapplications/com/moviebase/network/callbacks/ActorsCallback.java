package tobiapplications.com.moviebase.network.callbacks;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tobiapplications.com.moviebase.model.detail.ActorsResponse;
import tobiapplications.com.moviebase.ui.detail.DetailFragmentContract;

/**
 * Created by Tobias on 17.06.2017.
 */

public class ActorsCallback implements Callback<ActorsResponse> {

    private DetailFragmentContract.Presenter presenter;

    public ActorsCallback(DetailFragmentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResponse(Call<ActorsResponse> call, Response<ActorsResponse> response) {
        if (response.isSuccessful()) {
            presenter.displayActors(response.body());
        } else {
            presenter.displayError("ActorsCallback " + response.message());
        }
    }

    @Override
    public void onFailure(Call<ActorsResponse> call, Throwable t) {
        presenter.displayError("ActorsCallback");
    }
}
