package tobiapplications.com.moviebase.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tobiapplications.com.moviebase.model.Movie;
import tobiapplications.com.moviebase.model.MovieResponse;

/**
 * Created by Tobias on 10.06.2017.
 */

public class DataController {
    private TheMovieApi movieApi;
    private String api_key = "009aec9651189a3b2ed21b9b6a44bead";
    private static DataController dataController;

    public static DataController newInstance() {
        if (dataController == null) {
            dataController = new DataController();
        }

        return dataController;
    }

    public void buildMovieApi() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        movieApi = retrofit.create(TheMovieApi.class);
    }

    public TheMovieApi getMovieApi() {
        if (movieApi == null) {
            buildMovieApi();
        }

        return movieApi;
    }

    public void requestMovies() {
        Call<MovieResponse> movieCall = getMovieApi().movieResponseCall(api_key);
        movieCall.enqueue(new MovieCallback());
    }
}
