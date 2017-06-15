package tobiapplications.com.moviebase.network.callbacks;

import android.util.Log;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tobiapplications.com.moviebase.listener.OnOverviewMovieLoad;
import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;
import tobiapplications.com.moviebase.ui.overview.OverviewFragmentContract;

/**
 * Created by Tobias on 10.06.2017.
 */

public class OverviewCallback implements Callback<MovieOverviewResponse> {

    private OnOverviewMovieLoad listener;

    public OverviewCallback(OnOverviewMovieLoad listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<MovieOverviewResponse> call, Response<MovieOverviewResponse> response) {
        if (response.isSuccessful()) {
            listener.displayMovies(response.body());
        }
    }

    @Override
    public void onFailure(Call<MovieOverviewResponse> call, Throwable t) {
        Log.d("OverviewCallback", "onFailure");
        listener.displayError();
    }
}
