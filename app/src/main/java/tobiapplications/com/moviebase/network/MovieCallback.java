package tobiapplications.com.moviebase.network;

import android.util.Log;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tobiapplications.com.moviebase.model.MovieResponse;

/**
 * Created by Tobias on 10.06.2017.
 */

class MovieCallback implements Callback<MovieResponse> {

    @Override
    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
        Log.d("MovieCallback", "onResponse");
        MovieResponse movieResponse = response.body();
        Log.d("MovieCallback", "pages = " + movieResponse.getTotalResults());
    }

    @Override
    public void onFailure(Call<MovieResponse> call, Throwable t) {
        Log.d("MovieCallback", "onFailure");
    }
}
