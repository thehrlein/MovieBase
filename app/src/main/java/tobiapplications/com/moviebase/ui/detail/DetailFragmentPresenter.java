package tobiapplications.com.moviebase.ui.detail;

import android.content.Context;

import java.util.ArrayList;

import tobiapplications.com.moviebase.adapter.DetailAdapter;
import tobiapplications.com.moviebase.model.RecyclerItem;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.views.InfoView;

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


        parent.displayUiViews(detailItems);
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
