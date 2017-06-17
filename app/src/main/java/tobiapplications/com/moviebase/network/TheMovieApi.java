package tobiapplications.com.moviebase.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tobiapplications.com.moviebase.model.detail.ActorsResponse;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;

/**
 * Created by Tobias on 10.06.2017.
 */

public interface TheMovieApi {

    @GET("movie/popular")
    Call<MovieOverviewResponse> requestPopularMovies(@Query("api_key") String api_key, @Query("page") int pageToLoad);

    @GET("movie/top_rated")
    Call<MovieOverviewResponse> requestTopRatedMovies(@Query("api_key") String api_key, @Query("page") int pageToLoad);

    @GET("movie/{id}")
    Call<MovieDetailResponse> requestDetailInfo(@Path("id") int movieId, @Query("api_key") String api_key);

    @GET("movie/{id}/similar")
    Call<MovieOverviewResponse> requestSimilarMovies(@Path("id") int movieId, @Query("api_key") String api_key);

    @GET("movie/{id}/reviews")
    Call<ReviewResponse> requestMovieReviews(@Path("id") int movieId, @Query("api_key") String api_key);

    @GET("search/movie")
    Call<MovieOverviewResponse> requestSearchMovie(@Query("query") String query, @Query("api_key") String api_key);

    @GET("movie/{id}/credits")
    Call<ActorsResponse> requestActors(@Path("id") int movieId, @Query("api_key") String api_key);

}
