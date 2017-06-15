package tobiapplications.com.moviebase.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;

/**
 * Created by Tobias on 10.06.2017.
 */

public interface TheMovieApi {

    @GET ("movie/popular")
    Call<MovieOverviewResponse> requestPopularMovies(@Query("api_key") String api_key, @Query("page") int pageToLoad);

    @GET ("movie/top_rated")
    Call<MovieOverviewResponse> requestTopRatedMovies(@Query("api_key") String api_key, @Query("page") int pageToLoad);

    @GET("movie/{id}")
    Call<MovieDetailResponse> requestDetailInfo(@Path("id") int movieId, @Query("api_key") String api_key);

    @GET("movie/{id}/similar")
    Call<MovieOverviewResponse> requestSimilarMovies(@Path("id") int movieId, @Query("api_key") String api_key);

}
