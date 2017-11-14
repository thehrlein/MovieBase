package tobiapplications.com.moviebase.utils;

import android.support.annotation.IntDef;


/**
 * Created by Tobias on 11.06.2017.
 */

public class Constants {

    public static final String CLICKED_MOVIE = "clickedMovie";
    public static final String CLICKED_SERIE = "clickedSerie";
    public static final String RELEASED = "released";
    public static final String MOVIE_INSERT_TO_DATABASE = "movie_inserted_to_dataabse";
    public static final String SERIE_INSERT_TO_DATABASE = "serie_inserted_to_database";
    public static final String SEARCH_QUERY = "search_query";

    // movie db
    public static final String THE_MOVIE_DB = "theMovieDb";
    public static final String THE_MOVIE_QUERY_API_LABEL = "api_key";
    public static final String THE_MOVIE_QUERY_LANGUAGE_LABEL = "language";

    // youtube
    public static final String YOUTUBE = "youTube";
    public static final String YOUTUBE_API_QUERY_LABEL = "key";
    public static final String YOUTUBE_API_INFO_LABEL = "part";
    public static final String YOUTUBE_API_INFO_VALUE = "snippet,statistics";

    // fragments
    public static final String FRAGMENT_TAG = "fragment_tag";
    public static final String TYPE = "overview_type";

    @IntDef({Type.MOVIES, Type.SERIES})
    public @interface Type {
        int MOVIES = 0;
        int SERIES = 1;
    }

    @IntDef({Category.POPULAR, Category.TOP_RATED})
    public @interface Category {
        int POPULAR = 10;
        int TOP_RATED = 11;
    }
}
