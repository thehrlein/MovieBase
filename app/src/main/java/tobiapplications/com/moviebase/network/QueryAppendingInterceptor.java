package tobiapplications.com.moviebase.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.NetworkUtils;
import tobiapplications.com.moviebase.utils.SettingsUtils;

/**
 * Created by Tobias on 18.06.2017.
 */

public class QueryAppendingInterceptor implements Interceptor {

    private String apiFlag;

    public QueryAppendingInterceptor(String apiFlag) {
        this.apiFlag = apiFlag;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();
        HttpUrl url = null;

        if (apiFlag.equals(Constants.THE_MOVIE_DB)) {
            url = originalHttpUrl.newBuilder()
                    .addQueryParameter(Constants.THE_MOVIE_QUERY_API_LABEL, NetworkUtils.getMovieApiKey())
                    .addQueryParameter(Constants.THE_MOVIE_QUERY_LANGUAGE_LABEL, SettingsUtils.getAppLanguage())
                    .build();
        } else if (apiFlag.equals(Constants.YOUTUBE)) {
            url = originalHttpUrl.newBuilder()
                    .addQueryParameter(Constants.YOUTUBE_API_QUERY_LABEL, NetworkUtils.getYoutubeApiKey())
                    .addQueryParameter(Constants.YOUTUBE_API_INFO_LABEL, Constants.YOUTUBE_API_INFO_VALUE)
                    .build();
        }

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();

        return chain.proceed(request);
    }
}
