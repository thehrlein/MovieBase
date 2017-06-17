package tobiapplications.com.moviebase.network;


import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Tobias on 18.06.2017.
 */

public class LoggingInterceptor  {

    public static Interceptor getLogger() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return interceptor;
    }
}
