package tobiapplications.com.moviebase.utils;

import tobiapplications.com.moviebase.model.MovieDetailResponse;

/**
 * Created by Tobias on 11.06.2017.
 */

public class MovieDetailUtils {

    private static MovieDetailResponse movieDetailResponse;

    public static MovieDetailResponse getMovieDetailResponse() {
        return movieDetailResponse;
    }

    public static void setMovieDetailResponse(MovieDetailResponse movieDetailResponse) {
        MovieDetailUtils.movieDetailResponse = movieDetailResponse;
    }
}
