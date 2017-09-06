package tobiapplications.com.moviebase.ui.detail;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import tobiapplications.com.moviebase.adapter.DetailAdapter;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.RecyclerItem;
import tobiapplications.com.moviebase.model.detail.ActorsResponse;
import tobiapplications.com.moviebase.model.detail.FullTrailerItems;
import tobiapplications.com.moviebase.model.detail.Genre;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;
import tobiapplications.com.moviebase.model.detail.Trailer;
import tobiapplications.com.moviebase.model.detail.TrailersResponse;
import tobiapplications.com.moviebase.model.detail.YtSingleTrailerResponse;
import tobiapplications.com.moviebase.model.detail.YtThumbnailObject;
import tobiapplications.com.moviebase.model.detail.YtTrailerStatistic;
import tobiapplications.com.moviebase.model.detail.items.AdditionalInfoItem;
import tobiapplications.com.moviebase.model.detail.items.InfoItem;
import tobiapplications.com.moviebase.model.detail.items.SimilarMoviesItem;
import tobiapplications.com.moviebase.model.detail.items.SummaryItem;
import tobiapplications.com.moviebase.model.detail.items.TrailerItem;
import tobiapplications.com.moviebase.model.general_items.MoviePosterItem;
import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;
import tobiapplications.com.moviebase.network.DataManager;

/**
 * Created by Tobias on 14.06.2017.
 */

public class DetailFragmentPresenter implements DetailFragmentContract.Presenter {

    private int movieId;
    private Context context;
    private DetailFragmentContract.View parent;
    private int trailerResponseCount;
    private ArrayList<TrailerItem> trailerItems;

    public DetailFragmentPresenter(Context context, DetailFragmentContract.View parent) {
        this.context = context;
        this.parent = parent;
        trailerItems = new ArrayList<>();
    }

    public void init(MovieDetailResponse detailMovie) {
        buildUiFromResponse(detailMovie);
        requestMovieDownload();
        requestReviews();
        requestActors();
        requestTrailers();
    }

    @Override
    public void buildUiFromResponse(MovieDetailResponse detailMovie) {
        this.movieId = detailMovie.getId();

        ArrayList<DisplayableItem> detailItems = new ArrayList<>();

        detailItems.add(createInfoView(detailMovie));
        detailItems.add(createAdditionalInfoView(detailMovie));
        detailItems.add(createSummaryView(detailMovie));

        parent.displayUiViews(detailItems);
    }

    private DisplayableItem createAdditionalInfoView(MovieDetailResponse detailMovie) {
        String originalTitle = detailMovie.getOriginalTitle();
        int budget = detailMovie.getBudget();
        int revenue = detailMovie.getRevenue();
        ArrayList<Genre> genres = detailMovie.getGenres();
        String homepage = detailMovie.getHomepage();
        return new AdditionalInfoItem(originalTitle, budget, revenue, genres, homepage);
    }

    private DisplayableItem createSummaryView(MovieDetailResponse detailMovie) {
        String summary = detailMovie.getDescription();
        return new SummaryItem(summary);
    }

    private DisplayableItem createInfoView(MovieDetailResponse detailMovie) {
        String imagePath = detailMovie.getTitleImagePath();
        double voteAverage = detailMovie.getVoteAverage();
        int voteCount = detailMovie.getVoteCount();
        String release = detailMovie.getReleaseDate();
        boolean adult = detailMovie.isAdult();
        int runtime = detailMovie.getRuntime();
        String status = detailMovie.getStatus();
        return new InfoItem(imagePath, voteAverage, voteCount, release, adult, runtime, status);
    }

    @Override
    public void requestMovieDownload() {
        DataManager.getInstance().requestSimilarMovies(this, movieId);
    }

    @Override
    public void requestReviews() {
        DataManager.getInstance().requestReviews(this, movieId);
    }

    @Override
    public void requestActors() {
        DataManager.getInstance().requestActors(this, movieId);
    }


    private void requestTrailers() {
        DataManager.getInstance().requestTrailers(this, movieId);
    }

    @Override
    public void displayError(String message) {
        Toast.makeText(context, "Error: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayMovies(MovieOverviewResponse movieOverviewResponse) {
        if (movieOverviewResponse.getTotalResults() != 0) {
            ArrayList<MoviePosterItem> moviePosters = movieOverviewResponse.getMoviePosterItems();

            parent.displayUiView(new SimilarMoviesItem(moviePosters));
        }
    }

    @Override
    public void displayReviews(ReviewResponse response) {
        if (response.getTotalResults() != 0) {
            parent.displayUiView(response);
        }
    }

    @Override
    public void displayActors(ActorsResponse response) {
        if (response != null && !response.getActors().isEmpty()) {
            parent.displayUiView(response);
        }
    }

    @Override
    public void displayTrailers(TrailersResponse body) {
        ArrayList<Trailer> trailers = body.getTrailers();
        trailerResponseCount = trailers.size();
        for (Trailer trailer : trailers) {
            DataManager.getInstance().requestYoutubeTrailerInformation(this, trailer.getKey());
        }
    }

    @Override
    public void displaySingleYoutubeTrailer(YtSingleTrailerResponse body, String trailerKey) {
        String title = body.getTitle();
        YtThumbnailObject thumbnails = body.getThumbnails();
        YtTrailerStatistic statistics = body.getStatistics();

        TrailerItem trailerItem = new TrailerItem(title, trailerKey, thumbnails, statistics);
        trailerItems.add(trailerItem);
        if (trailerItems.size() == trailerResponseCount) {
            parent.displayUiView(new FullTrailerItems(trailerItems));
        }
    }
}
