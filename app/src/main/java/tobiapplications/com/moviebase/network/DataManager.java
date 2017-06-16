package tobiapplications.com.moviebase.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tobiapplications.com.moviebase.listener.OnOverviewMovieLoad;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;
import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;
import tobiapplications.com.moviebase.network.callbacks.DetailCallback;
import tobiapplications.com.moviebase.network.callbacks.OverviewCallback;
import tobiapplications.com.moviebase.network.callbacks.ReviewCallback;
import tobiapplications.com.moviebase.ui.detail.DetailActivityPresenter;
import tobiapplications.com.moviebase.ui.detail.DetailFragmentContract;
import tobiapplications.com.moviebase.ui.overview.OverviewFragmentContract;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 10.06.2017.
 */

public class DataManager {
    private TheMovieApi movieApi;
    private static DataManager dataManager;
    public static final String POPULAR_MOVIES = "pop_movies";
    public static final String TOP_RATED_MOVIES = "top_movies";
    public static final String SINGLE_MOVIE = "single_movie";


    public static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }

        return dataManager;
    }

    private void buildMovieApi() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new Interceptor() {

                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .build();
                        return chain.proceed(request);
                    }
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.MOVIE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        movieApi = retrofit.create(TheMovieApi.class);
    }

    private TheMovieApi getMovieApi() {
        if (movieApi == null) {
            buildMovieApi();
        }

        return movieApi;
    }

    public void requestPopularMovies(OnOverviewMovieLoad listener, int pageToLoad) {
        Call<MovieOverviewResponse> popularMovieCall = getMovieApi().requestPopularMovies(NetworkUtils.getKey(), pageToLoad);
        popularMovieCall.enqueue(new OverviewCallback(listener));
    }

    public void requestTopRatedMovies(OnOverviewMovieLoad listener, int pageToLoad) {
        Call<MovieOverviewResponse> topRatedMovieCall = getMovieApi().requestTopRatedMovies(NetworkUtils.getKey(), pageToLoad);
        topRatedMovieCall.enqueue(new OverviewCallback(listener));
    }

    public void requestSingleMovie(DetailActivityPresenter presenter, int movieId) {
        Call<MovieDetailResponse> singleMovieCall = getMovieApi().requestDetailInfo(movieId, NetworkUtils.getKey());
        singleMovieCall.enqueue(new DetailCallback(presenter));
    }

    public void requestSimilarMovies(OnOverviewMovieLoad listener, int movieId) {
        Call<MovieOverviewResponse> similarMovieCall = getMovieApi().requestSimilarMovies(movieId, NetworkUtils.getKey());
        similarMovieCall.enqueue(new OverviewCallback(listener));
    }

    public void requestReviews(int movieId) {
        Call<ReviewResponse> reviewResponseCall = getMovieApi().requestMovieReviews(movieId, NetworkUtils.getKey());
        reviewResponseCall.enqueue(new ReviewCallback());
    }

}
