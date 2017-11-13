package tobiapplications.com.moviebase.network;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tobiapplications.com.moviebase.model.detail.ActorsResponse;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;
import tobiapplications.com.moviebase.model.detail.SeriesDetailResponse;
import tobiapplications.com.moviebase.model.detail.TrailersResponse;
import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;

/**
 * Created by Tobias on 10.06.2017.
 */

public interface TheMovieApi {

    @GET("movie/popular")
    Single<MovieOverviewResponse> requestPopularMovies(@Query("page") int pageToLoad);

    @GET("tv/popular")
    Single<MovieOverviewResponse> requestPopularSeries(@Query("page") int pageToLoad);

    @GET("movie/top_rated")
    Single<MovieOverviewResponse> requestTopRatedMovies(@Query("page") int pageToLoad);

    @GET("tv/top_rated")
    Single<MovieOverviewResponse> requestTopRatedSeries(@Query("page") int pageToLoad);

    @GET("movie/{id}")
    Single<MovieDetailResponse> requestMovieDetails(@Path("id") int movieId);

    @GET("tv/{id}")
    Single<SeriesDetailResponse> requestSeriesDetails(@Path("id") int movieId);

    @GET("movie/{id}/similar")
    Single<MovieOverviewResponse> requestSimilarMovies(@Path("id") int movieId);

    @GET("tv/{id}/similar")
    Single<MovieOverviewResponse> requestSimilarSeries(@Path("id") int movieId);

    @GET("movie/{id}/reviews")
    Single<ReviewResponse> requestMovieReviews(@Path("id") int movieId);

    @GET("search/movie")
    Single<MovieOverviewResponse> requestSearchMovie(@Query("query") String query);

    @GET("search/tv")
    Single<MovieOverviewResponse> requestSearchSerie(@Query("query") String query);

    @GET("movie/{id}/credits")
    Single<ActorsResponse> requestActors(@Path("id") int movieId);

    @GET("movie/{id}/videos")
    Single<TrailersResponse> requestMovieTrailers(@Path("id") int movieId);

    @GET("tv/{id}/videos")
    Single<TrailersResponse> requestSerieTrailers(@Path("id") int serieId);

}
