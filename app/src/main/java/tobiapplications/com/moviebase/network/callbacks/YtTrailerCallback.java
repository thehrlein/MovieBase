package tobiapplications.com.moviebase.network.callbacks;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tobiapplications.com.moviebase.model.detail.YtSingleTrailerResponse;
import tobiapplications.com.moviebase.ui.detail.DetailFragmentContract;

/**
 * Created by Tobias on 19.06.2017.
 */

public class YtTrailerCallback implements Callback<YtSingleTrailerResponse> {

    private DetailFragmentContract.Presenter presenter;
    private String trailerKey;

    public YtTrailerCallback(DetailFragmentContract.Presenter presenter, String trailerKey) {
        this.presenter = presenter;
        this.trailerKey = trailerKey;
    }

    @Override
    public void onResponse(Call<YtSingleTrailerResponse> call, Response<YtSingleTrailerResponse> response) {
        if (response.isSuccessful()) {
            presenter.displaySingleYoutubeTrailer(response.body(), trailerKey);
        } else {
            presenter.displayError();
        }
    }

    @Override
    public void onFailure(Call<YtSingleTrailerResponse> call, Throwable t) {
        presenter.displayError();
    }
}
