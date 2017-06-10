package tobiapplications.com.moviebase.network;

import android.provider.ContactsContract;
import android.util.Log;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tobiapplications.com.moviebase.model.MovieResponse;
import tobiapplications.com.moviebase.ui.movie_overview.MoviePresenter;

/**
 * Created by Tobias on 10.06.2017.
 */

class MovieCallback implements Callback<MovieResponse> {

    private MoviePresenter presenter;
    private int category;

    public MovieCallback(MoviePresenter presenter, int category) {
        this.presenter = presenter;
        this.category = category;
    }

    @Override
    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
        if (response.isSuccessful()) {
            displaySuccess(response.body());
        }
    }

    private void displaySuccess(MovieResponse response) {
        if (category == DataManager.POPULAR_MOVIES) {
            presenter.displayMovies(response);
        } else  if (category == DataManager.TOP_RATED_MOVIES) {

        }
    }

    @Override
    public void onFailure(Call<MovieResponse> call, Throwable t) {
        Log.d("MovieCallback", "onFailure");
    }
}
