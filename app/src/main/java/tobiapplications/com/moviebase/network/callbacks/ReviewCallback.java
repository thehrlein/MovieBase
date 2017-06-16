package tobiapplications.com.moviebase.network.callbacks;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;

/**
 * Created by Tobias on 16.06.2017.
 */

public class ReviewCallback implements Callback<ReviewResponse> {

    @Override
    public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
        if (response.isSuccessful()) {

        }
    }

    @Override
    public void onFailure(Call<ReviewResponse> call, Throwable t) {

    }
}
