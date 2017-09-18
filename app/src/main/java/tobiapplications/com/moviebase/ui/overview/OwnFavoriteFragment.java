package tobiapplications.com.moviebase.ui.overview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tobiapplications.com.moviebase.adapter.OverviewTabAdapter;
import tobiapplications.com.moviebase.database.MoviesContract;
import tobiapplications.com.moviebase.database.SeriesContract;
import tobiapplications.com.moviebase.databinding.FragmentOverviewTabBinding;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.ui.detail.DetailActivity;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.GeneralUtils;
import tobiapplications.com.moviebase.utils.SQLUtils;

/**
 * Created by Tobias on 09.06.2017.
 */

public class OwnFavoriteFragment extends Fragment implements OverviewTabFragmentContract.DatabaseView {

    private final String TAG = OwnFavoriteFragment.class.getSimpleName();
    private FragmentOverviewTabBinding bind;
    private Context context;
    private OverviewTabAdapter adapter;
    private OwnFavoritePresenter presenter;
    private static final int MOVIE_CURSOR_LOADER_ID = 123;
    private static final int SERIE_CURSOR_LOADER_ID = 456;
    private Constants.OverviewType overviewType;


    public static Fragment newInstance(Constants.OverviewType overviewType) {
        OwnFavoriteFragment ownFavoriteFragment = new OwnFavoriteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.OVERVIEW_TYPE, overviewType);
        ownFavoriteFragment.setArguments(bundle);
        return ownFavoriteFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        overviewType = parseArguments(getArguments());
        presenter = new OwnFavoritePresenter(this, context);
    }

    private Constants.OverviewType parseArguments(Bundle arguments) {
        if (arguments == null) {
            return null;
        }

        if (arguments.containsKey(Constants.OVERVIEW_TYPE)) {
            return (Constants.OverviewType) arguments.getSerializable(Constants.OVERVIEW_TYPE);
        }

        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentOverviewTabBinding.inflate(inflater);

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setGridViewAndAdapter();
    }

    @Override
    public void setGridViewAndAdapter() {
        int howMuchColumns = GeneralUtils.getHowMuchColumnsForOverviewMovies(context);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(context, howMuchColumns);
        bind.recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new OverviewTabAdapter(context, bind.recyclerView, this);
        bind.recyclerView.setAdapter(adapter);
    }

    @Override
    public void setMovies(ArrayList<MovieOverviewModel> movies) {
        adapter.removeLoadingItem();
        adapter.setMovies(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setSeries(ArrayList<MovieOverviewModel> series) {
        adapter.removeLoadingItem();
        adapter.setSeries(series);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading(boolean load) {
        if (bind.progressBarLoading != null && bind.recyclerView != null) {
            bind.progressBarLoading.setVisibility(load ? View.VISIBLE : View.GONE);
            bind.recyclerView.setVisibility(load ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onMovieClick(int id) {
        Intent openMovieDetails = new Intent(context, DetailActivity.class);
        openMovieDetails.putExtra(Constants.CLICKED_MOVIE, id);
        openMovieDetails.putExtra(Constants.OVERVIEW_TYPE, overviewType);
        startActivity(openMovieDetails);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == MOVIE_CURSOR_LOADER_ID) {
            return new CursorLoader(
                    context,
                    MoviesContract.MovieEntry.CONTENT_URI,
                    SQLUtils.selectAllMovies,
                    null,
                    null,
                    null);
        } else {
            return new CursorLoader(
                    context,
                    SeriesContract.SeriesEntry.CONTENT_URI,
                    SQLUtils.selectAllSeries,
                    null,
                    null,
                    null);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int id = loader.getId();
        Constants.OverviewType overviewType = id == MOVIE_CURSOR_LOADER_ID ? Constants.OverviewType.MOVIES : Constants.OverviewType.SERIES;
        presenter.onDatabaseLoadFinished(data, overviewType);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.setMovies(null);
    }

    @Override
    public void startLoader(Constants.OverviewType overviewType) {
        if (overviewType == Constants.OverviewType.MOVIES) {
            getActivity().getSupportLoaderManager().restartLoader(MOVIE_CURSOR_LOADER_ID, null, this);
        } else {
            getActivity().getSupportLoaderManager().restartLoader(SERIE_CURSOR_LOADER_ID, null, this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(context).registerReceiver(movieInsertedIntoDatabase, new IntentFilter(Constants.MOVIE_INSERT_TO_DATABASE));
        LocalBroadcastManager.getInstance(context).registerReceiver(serieInsertedIntoDatabase, new IntentFilter(Constants.SERIE_INSERT_TO_DATABASE));
        setMovies(null);
        startLoader(overviewType);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(movieInsertedIntoDatabase);
        LocalBroadcastManager.getInstance(context).unregisterReceiver(serieInsertedIntoDatabase);
    }

    public void hideNoFavoriteAvailable() {
        bind.noFavoriteAvailable.setVisibility(View.GONE);
    }

    @Override
    public void showNoFavoriteAvailable(String text) {
        bind.noFavoriteAvailable.setVisibility(View.VISIBLE);
        bind.noFavoriteAvailable.setText(text);
    }

    private BroadcastReceiver movieInsertedIntoDatabase = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onResume();
        }
    };

    private BroadcastReceiver serieInsertedIntoDatabase = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onResume();
        }
    };
}
