package tobiapplications.com.moviebase.ui.detail;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.detail.ActorsResponse;
import tobiapplications.com.moviebase.model.detail.FullTrailerItems;
import tobiapplications.com.moviebase.model.detail.Genre;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.ReviewResponse;
import tobiapplications.com.moviebase.model.detail.Season;
import tobiapplications.com.moviebase.model.detail.SeriesDetailResponse;
import tobiapplications.com.moviebase.model.detail.Trailer;
import tobiapplications.com.moviebase.model.detail.TrailersResponse;
import tobiapplications.com.moviebase.model.detail.YtSingleTrailerResponse;
import tobiapplications.com.moviebase.model.detail.YtThumbnailObject;
import tobiapplications.com.moviebase.model.detail.YtTrailerStatistic;
import tobiapplications.com.moviebase.model.detail.items.movie.AdditionalMovieInfoItem;
import tobiapplications.com.moviebase.model.detail.items.movie.MovieInfoItem;
import tobiapplications.com.moviebase.model.detail.items.serie.AdditionalSerieInfoItem;
import tobiapplications.com.moviebase.model.detail.items.serie.SeasonsItem;
import tobiapplications.com.moviebase.model.detail.items.serie.SerieInfoItem;
import tobiapplications.com.moviebase.model.detail.items.SimilarMoviesItem;
import tobiapplications.com.moviebase.model.detail.items.SummaryItem;
import tobiapplications.com.moviebase.model.detail.items.movie.TrailerItem;
import tobiapplications.com.moviebase.model.general_items.MoviePosterItem;
import tobiapplications.com.moviebase.model.overview.MovieOverviewResponse;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 14.06.2017.
 */

public class DetailFragmentPresenter implements DetailFragmentContract.Presenter {

    private int id;
    private Context context;
    private DetailFragmentContract.View parent;
    private int trailerResponseCount;
    private ArrayList<TrailerItem> trailerItems;
    private Constants.OverviewType overviewType;

    public DetailFragmentPresenter(Context context, DetailFragmentContract.View parent, Constants.OverviewType overviewType) {
        this.context = context;
        this.parent = parent;
        this.overviewType = overviewType;
        trailerItems = new ArrayList<>();
    }

    @Override
    public void init(MovieDetailResponse detailMovie) {
        buildUiFromResponse(detailMovie);
        requestMoviesDownload();
        requestReviews();
        requestActors();
        requestTrailers();
    }

    @Override
    public void init(SeriesDetailResponse detailSerie) {
        buildUiFromResponse(detailSerie);
        requestSeriesDownload();
        requestTrailers();
    }



    private void buildUiFromResponse(MovieDetailResponse detailMovie) {
        this.id = detailMovie.getId();

        ArrayList<DisplayableItem> detailItems = new ArrayList<>();

        detailItems.add(createInfoView(detailMovie));
        detailItems.add(createAdditionalInfoView(detailMovie));
        detailItems.add(createSummaryView(detailMovie.getDescription()));

        parent.displayUiViews(detailItems);
    }

    private void buildUiFromResponse(SeriesDetailResponse detailSerie) {
        this.id = detailSerie.getId();

        ArrayList<DisplayableItem> detailItems = new ArrayList<>();
        detailItems.add(createInfoView(detailSerie));
        detailItems.add(createAdditionalInfoView(detailSerie));
        detailItems.add(createSummaryView(detailSerie.getDescription()));
        detailItems.add(createSeriesView(detailSerie.getSeasons()));

        parent.displayUiViews(detailItems);
    }

    private DisplayableItem createSeriesView(ArrayList<Season> seasons) {
        SeasonsItem seasonsItem = new SeasonsItem(seasons);
        return seasonsItem;
    }

    private DisplayableItem createAdditionalInfoView(MovieDetailResponse movie) {
        String originalTitle = movie.getOriginalTitle();
        int budget = movie.getBudget();
        int revenue = movie.getRevenue();
        ArrayList<Genre> genres = movie.getGenres();
        String homepage = movie.getHomepage();
        return new AdditionalMovieInfoItem(originalTitle, budget, revenue, genres, homepage);
    }

    private DisplayableItem createAdditionalInfoView(SeriesDetailResponse serie) {
        String originalTitle = serie.getOriginalName();
        String episodes = serie.getNumberOfEpisodes();
        String seasons = serie.getNumberOfSeasons();
        ArrayList<Genre> genres = serie.getGenres();
        String homepage = serie.getHomepage();
        return new AdditionalSerieInfoItem(originalTitle, episodes, seasons, genres, homepage);
    }

    private DisplayableItem createSummaryView(String description) {
        return new SummaryItem(description);
    }

    private DisplayableItem createInfoView(MovieDetailResponse movie) {
        String imagePath = movie.getTitleImagePath();
        double voteAverage = movie.getVoteAverage();
        int voteCount = movie.getVoteCount();
        String release = movie.getReleaseDate();
        boolean adult = movie.isAdult();
        int runtime = movie.getRuntime();
        String status = movie.getStatus();
        return new MovieInfoItem(imagePath, voteAverage, voteCount, release, adult, runtime, status);
    }

    private DisplayableItem createInfoView(SeriesDetailResponse serie) {
        double voteAverage = serie.getVoteAverage();
        int voteCount = serie.getVoteCount();
        String firstAirDate = serie.getFirstAirDate();
        String lastAirTime = serie.getLastAirTime();
        boolean adult = serie.isAdult();
        String status = serie.getStatus();

        return new SerieInfoItem(voteAverage, voteCount, firstAirDate, lastAirTime, adult, status);
    }

    @Override
    public void requestMoviesDownload() {
        DataManager.getInstance().requestSimilarMovies(this, id);
    }

    @Override
    public void requestSeriesDownload() {
        DataManager.getInstance().requestSimilarSeries(this, id);
    }

    @Override
    public void requestReviews() {
        DataManager.getInstance().requestReviews(this, id);
    }

    @Override
    public void requestActors() {
        DataManager.getInstance().requestActors(this, id);
    }


    private void requestTrailers() {
        if (overviewType == Constants.OverviewType.MOVIES) {
            DataManager.getInstance().requestMovieTrailers(this, id);
        } else if (overviewType == Constants.OverviewType.SERIES) {
            DataManager.getInstance().requestSerieTrailer(this, id);
        }
    }

    @Override
    public void displayError(String message) {
        Toast.makeText(context, "Error: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayMovies(MovieOverviewResponse movieOverviewResponse) {
        if (movieOverviewResponse.getTotalResults() != 0) {
            ArrayList<MoviePosterItem> moviePosters;

            if (overviewType == Constants.OverviewType.MOVIES) {
                moviePosters = movieOverviewResponse.getMoviePosterItems();
            } else {
                moviePosters = movieOverviewResponse.getSeriePosterItems();
            }

            String similarTitle;
            if (overviewType == Constants.OverviewType.MOVIES) {
                similarTitle = context.getString(R.string.similar_movies, context.getString(R.string.movie_title));
            } else {
                similarTitle = context.getString(R.string.similar_movies, context.getString(R.string.series_title));
            }

            parent.displayUiView(new SimilarMoviesItem(moviePosters, similarTitle));
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
