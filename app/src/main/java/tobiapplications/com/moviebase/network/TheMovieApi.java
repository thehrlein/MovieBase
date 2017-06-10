package tobiapplications.com.moviebase.network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import tobiapplications.com.moviebase.model.MovieResponse;

/**
 * Created by Tobias on 10.06.2017.
 */

public interface TheMovieApi {

    @GET ("movie/popular")
    Call<MovieResponse> movieResponseCall(@Query("api_key") String api_key);

}
