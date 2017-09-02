package tobiapplications.com.moviebase.network.callbacks;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tobiapplications.com.moviebase.listener.OnOverviewMovieLoadListener;
import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;

/**
 * Created by Tobias on 10.06.2017.
 */

public class OverviewCallback implements Callback<MovieOverviewResponse> {

    private OnOverviewMovieLoadListener listener;

    public OverviewCallback(OnOverviewMovieLoadListener listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<MovieOverviewResponse> call, Response<MovieOverviewResponse> response) {
        if (response.isSuccessful()) {
            listener.displayMovies(response.body());
        } else {
            listener.displayError("OverviewCallback " + response.message());
        }
    }

    @Override
    public void onFailure(Call<MovieOverviewResponse> call, Throwable t) {
        listener.displayError("OverviewCallback");
    }
}
