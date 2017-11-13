package tobiapplications.com.moviebase.network;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tobiapplications.com.moviebase.model.detail.YtSingleTrailerResponse;

/**
 * Created by Tobias on 19.06.2017.
 */

public interface YoutubeApi {

    @GET("videos")
    Single<YtSingleTrailerResponse> requestSingleTrailer(@Query("id") String trailerKey);
}
