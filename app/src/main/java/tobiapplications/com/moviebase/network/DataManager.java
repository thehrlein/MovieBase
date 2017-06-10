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
import tobiapplications.com.moviebase.model.MovieResponse;
import tobiapplications.com.moviebase.ui.movie_overview.MoviePresenter;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 10.06.2017.
 */

public class DataManager {
    private TheMovieApi movieApi;
    private static DataManager dataManager;

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

    public void requestMovies(MoviePresenter presenter) {
        Call<MovieResponse> movieCall = getMovieApi().movieResponseCall(NetworkUtils.getKey());
        movieCall.enqueue(new MovieCallback(presenter));
    }
}
