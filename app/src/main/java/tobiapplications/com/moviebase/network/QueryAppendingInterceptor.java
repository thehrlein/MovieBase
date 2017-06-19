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

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter(Constants.QUERY_API_KEY, NetworkUtils.getKey())
                .addQueryParameter(Constants.QUERY_LANGUAGE, SettingsUtils.getAppLanguage())
                .build();

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();

        return chain.proceed(request);
    }
}
