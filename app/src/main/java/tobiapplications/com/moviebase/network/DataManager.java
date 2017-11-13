package tobiapplications.com.moviebase.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tobiapplications.com.moviebase.model.detail.ActorsResponse;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;
import tobiapplications.com.moviebase.model.detail.SeriesDetailResponse;
import tobiapplications.com.moviebase.model.detail.TrailersResponse;
import tobiapplications.com.moviebase.model.detail.YtSingleTrailerResponse;
import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 10.06.2017.
 */

public class DataManager {

    private TheMovieApi movieApi;
    private YoutubeApi youtubeApi;
    private static DataManager dataManager;

    public static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }

        return dataManager;
    }

    public void buildApi(String apiKey) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new QueryAppendingInterceptor(apiKey))
                .addInterceptor(LoggingInterceptor.getLogger())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.getApiBaseUrl(apiKey))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        if (apiKey.equals(Constants.THE_MOVIE_DB)) {
            movieApi = retrofit.create(TheMovieApi.class);
        } else if (apiKey.equals(Constants.YOUTUBE)) {
            youtubeApi = retrofit.create(YoutubeApi.class);
        }
    }

    public Single<MovieOverviewResponse> requestPopularMovies(int pageToLoad) {
        return movieApi.requestPopularMovies(pageToLoad);
    }

    public Single<MovieOverviewResponse> requestPopularSeries(int pageToLoad) {
        return movieApi.requestPopularSeries(pageToLoad);
    }

    public Single<MovieOverviewResponse> requestTopRatedMovies(int pageToLoad) {
        return movieApi.requestTopRatedMovies(pageToLoad);
    }

    public Single<MovieOverviewResponse> requestTopRatedSeries(int pageToLoad) {
        return movieApi.requestTopRatedSeries(pageToLoad);
    }

    public Single<MovieDetailResponse> requestSingleMovie(int movieId) {
        return movieApi.requestMovieDetails(movieId);
    }

    public Single<SeriesDetailResponse> requestSingleSerie(int serieId) {
        return movieApi.requestSeriesDetails(serieId);
    }

    public Single<MovieOverviewResponse> requestSimilarMovies(int movieId) {
        return movieApi.requestSimilarMovies(movieId);
    }

    public Single<MovieOverviewResponse> requestSimilarSeries(int movieId) {
        return movieApi.requestSimilarSeries(movieId);
    }

    public Single<ReviewResponse> requestReviews(int movieId) {
        return movieApi.requestMovieReviews(movieId);
    }

    public Single<MovieOverviewResponse> requestSearchMovie(String query) {
        return movieApi.requestSearchMovie(query);
    }

    public Single<MovieOverviewResponse> requestSearchSerie(String query) {
        return movieApi.requestSearchSerie(query);
    }

    public Single<ActorsResponse> requestActors(int movieId) {
        return movieApi.requestActors(movieId);
    }

    public Single<TrailersResponse> requestMovieTrailers(int movieId) {
        return movieApi.requestMovieTrailers(movieId);
    }

    public Single<TrailersResponse> requestSerieTrailer(int serieId) {
        return movieApi.requestSerieTrailers(serieId);
    }

    public Single<YtSingleTrailerResponse> requestYoutubeTrailerInformation(String trailerKey) {
        return youtubeApi.requestSingleTrailer(trailerKey);
    }
}
