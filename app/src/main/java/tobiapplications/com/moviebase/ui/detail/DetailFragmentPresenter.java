package tobiapplications.com.moviebase.ui.detail;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import tobiapplications.com.moviebase.adapter.DetailAdapter;
import tobiapplications.com.moviebase.model.RecyclerItem;
import tobiapplications.com.moviebase.model.detail.ActorsResponse;
import tobiapplications.com.moviebase.model.detail.Genre;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;
import tobiapplications.com.moviebase.model.detail.TrailerResponse;
import tobiapplications.com.moviebase.model.detail.items.AdditionalInfoItem;
import tobiapplications.com.moviebase.model.detail.items.InfoItem;
import tobiapplications.com.moviebase.model.detail.items.SimilarMoviesItem;
import tobiapplications.com.moviebase.model.detail.items.SummaryItem;
import tobiapplications.com.moviebase.model.general_items.MoviePosterItem;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;
import tobiapplications.com.moviebase.network.DataManager;

/**
 * Created by Tobias on 14.06.2017.
 */

public class DetailFragmentPresenter implements DetailFragmentContract.Presenter {

    private int movieId;
    private Context context;
    private DetailFragmentContract.View parent;

    public DetailFragmentPresenter(Context context, DetailFragmentContract.View parent) {
        this.context = context;
        this.parent = parent;
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

        ArrayList<RecyclerItem> detailItems = new ArrayList<>();

        detailItems.add(new RecyclerItem(DetailAdapter.VIEW_TYPE_INFO, createInfoView(detailMovie)));
        detailItems.add(new RecyclerItem(DetailAdapter.VIEW_TYPE_ADDITIONAL_INFO, createAdditionalInfoView(detailMovie)));
        detailItems.add(new RecyclerItem(DetailAdapter.VIEW_TYPE_SUMMARY, createSummaryView(detailMovie)));

        parent.displayUiViews(detailItems);
    }

    private AdditionalInfoItem createAdditionalInfoView(MovieDetailResponse detailMovie) {
        String originalTitle = detailMovie.getOriginalTitle();
        int budget = detailMovie.getBudget();
        int revenue = detailMovie.getRevenue();
        ArrayList<Genre> genres = detailMovie.getGenres();
        String homepage = detailMovie.getHomepage();
        return new AdditionalInfoItem(originalTitle, budget, revenue, genres, homepage);
    }

    private SummaryItem createSummaryView(MovieDetailResponse detailMovie) {
        String summary = detailMovie.getDescription();
        return new SummaryItem(summary);
    }

    private InfoItem createInfoView(MovieDetailResponse detailMovie) {
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
    public void displayError() {
        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayMovies(MovieOverviewResponse movieOverviewResponse) {
        if (movieOverviewResponse.getTotalResults() != 0) {
            ArrayList<MoviePosterItem> moviePosters = movieOverviewResponse.getMoviePosterItems();

            RecyclerItem item = new RecyclerItem(DetailAdapter.VIEW_TYPE_SIMILAR_MOVIES, new SimilarMoviesItem(moviePosters));
            parent.displayUiView(item);
        }
    }

    @Override
    public void displayReviews(ReviewResponse response) {
        if (response.getTotalResults() != 0) {
            RecyclerItem item = new RecyclerItem(DetailAdapter.VIEW_TYPE_REVIEWS, response);
            parent.displayUiView(item);
        }
    }

    @Override
    public void displayActors(ActorsResponse response) {
        if (response != null && !response.getActors().isEmpty()) {
            RecyclerItem item = new RecyclerItem(DetailAdapter.VIEW_TYPE_ACTORS, response);
            parent.displayUiView(item);
        }
    }

    @Override
    public void displayTrailers(TrailerResponse body) {
        TrailerResponse test = body;
    }
}
