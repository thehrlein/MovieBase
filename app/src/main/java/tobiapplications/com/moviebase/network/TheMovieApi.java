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
    Call<MovieResponse> popularMovieResponseCall(@Query("api_key") String api_key);

    @GET ("movie/top_rated")
    Call<MovieResponse> topRatedMovieResponseCall(@Query("api_key") String api_key);

}
