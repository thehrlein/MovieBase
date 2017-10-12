package tobiapplications.com.moviebase.ui.detail;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

import timber.log.Timber;
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
import tobiapplications.com.moviebase.utils.StringUtils;

import static tobiapplications.com.moviebase.utils.GeneralUtils.*;

/**
 * Created by Tobias on 14.06.2017.
 */

public class DetailFragmentPresenter implements DetailFragmentContract.Presenter {

    private int id;
    private Context context;
    private DetailFragmentContract.View parent;
    private int trailerResponseCount;
    private ArrayList<TrailerItem> trailerItems;
    private int type;
    private MovieDetailResponse detailMovie;
    private SeriesDetailResponse detailSerie;


    public DetailFragmentPresenter(Context context, DetailFragmentContract.View parent, Bundle arguments) {
        this.context = context;
        this.parent = parent;
        trailerItems = new ArrayList<>();
        parseArguments(arguments);
    }

    private void parseArguments(Bundle arguments) {
        type = arguments.getInt(Constants.TYPE);
        if (isMovie(type)) {
            detailMovie = (MovieDetailResponse) arguments.get(Constants.CLICKED_MOVIE);
        } else if (isSerie(type)){
            detailSerie = (SeriesDetailResponse) arguments.get(Constants.CLICKED_SERIE);
        }
    }

    @Override
    public void init() {
        parent.setAdapter(type);
        if (isMovie(type)) {
            initMovie(detailMovie);
        } else if (isSerie(type)) {
            initSerie(detailSerie);
        }
    }

    private void initMovie(MovieDetailResponse detailMovie) {
        if (detailMovie == null) {
            return;
        }

        buildUiFromResponse(detailMovie);
        requestReviews();
        requestActors();
        requestTrailers();
        requestMoviesDownload();
    }

    private void initSerie(SeriesDetailResponse detailSerie) {
        if (detailSerie == null) {
            return;
        }

        buildUiFromResponse(detailSerie);
        requestTrailers();
        requestSeriesDownload();
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
        if (seasons == null || seasons.isEmpty()) {
            return null;
        }

        return new SeasonsItem(seasons);
    }

    private DisplayableItem createAdditionalInfoView(MovieDetailResponse movie) {
        String originalTitle = movie.getOriginalTitle();
        int budget = movie.getBudget();
        long revenue = movie.getRevenue();
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
        if (StringUtils.nullOrEmpty(description)) {
            return null;
        }
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

        return new SerieInfoItem(voteAverage, voteCount, firstAirDate, lastAirTime, adult);
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
        if (isMovie(type)) {
            DataManager.getInstance().requestMovieTrailers(this, id);
        } else if (isSerie(type)) {
            DataManager.getInstance().requestSerieTrailer(this, id);
        }
    }

    @Override
    public void displayError(String message) {
        Timber.d("Error: " + message);
    }

    @Override
    public void displayPosterItems(MovieOverviewResponse movieOverviewResponse) {
        if (movieOverviewResponse.getTotalResults() != 0) {
            ArrayList<MoviePosterItem> moviePosters = null;
            String similarTitle = "";

            if (isMovie(type)) {
                moviePosters = movieOverviewResponse.getMoviePosterItems();
                similarTitle = context.getString(R.string.similar_movies, context.getString(R.string.movie_title));
            } else if (isSerie(type)){
                moviePosters = movieOverviewResponse.getSeriePosterItems();
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
