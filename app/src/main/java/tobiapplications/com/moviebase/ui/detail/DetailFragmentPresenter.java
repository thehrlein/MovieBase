package tobiapplications.com.moviebase.ui.detail;

import android.content.Context;

import java.util.ArrayList;

import tobiapplications.com.moviebase.adapter.DetailAdapter;
import tobiapplications.com.moviebase.model.RecyclerItem;
import tobiapplications.com.moviebase.model.detail.Genre;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.views.AdditionalInfoView;
import tobiapplications.com.moviebase.model.detail.views.InfoView;
import tobiapplications.com.moviebase.model.detail.views.SummaryView;

/**
 * Created by Tobias on 14.06.2017.
 */

public class DetailFragmentPresenter implements DetailFragmentContract.Presenter {

    private MovieDetailResponse detailMovie;
    private Context context;
    private DetailFragmentContract.View parent;

    public DetailFragmentPresenter(Context context, DetailFragmentContract.View parent) {
        this.context = context;
        this.parent = parent;
    }

    @Override
    public void buildUiFromResponse(MovieDetailResponse detailMovie) {
        this.detailMovie = detailMovie;

        ArrayList<RecyclerItem> detailItems = new ArrayList<>();

        detailItems.add(new RecyclerItem(DetailAdapter.VIEW_TYPE_INFO, createInfoView(detailMovie)));
        detailItems.add(new RecyclerItem(DetailAdapter.VIEW_TYPE_SUMMARY, createSummaryView(detailMovie)));
        detailItems.add(new RecyclerItem(DetailAdapter.VIEW_TYPE_ADDITIONAL_INFO, createAdditionalInfoView(detailMovie)));

        parent.displayUiViews(detailItems);
    }

    private AdditionalInfoView createAdditionalInfoView(MovieDetailResponse detailMovie) {
        String originalTitle = detailMovie.getOriginalTitle();
        int budget = detailMovie.getBudget();
        int revenue = detailMovie.getRevenue();
        ArrayList<Genre> genres = detailMovie.getGenres();
        String homepage = detailMovie.getHomepage();
        return new AdditionalInfoView(originalTitle, budget, revenue, genres, homepage);
    }

    private SummaryView createSummaryView(MovieDetailResponse detailMovie) {
        String summary = detailMovie.getDescription();
        return new SummaryView(summary);
    }

    private InfoView createInfoView(MovieDetailResponse detailMovie) {
        String imagePath = detailMovie.getTitleImagePath();
        double voteAverage = detailMovie.getVoteAverage();
        int voteCount = detailMovie.getVoteCount();
        String release = detailMovie.getReleaseDate();
        boolean adult = detailMovie.isAdult();
        int runtime = detailMovie.getRuntime();
        return new InfoView(imagePath, voteAverage, voteCount, release, adult, runtime, detailMovie.getStatus());
    }
}
