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
import tobiapplications.com.moviebase.model.overview.PosterOverviewItem;
import tobiapplications.com.moviebase.ui.detail.DetailActivity;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.GeneralUtils;
import tobiapplications.com.moviebase.utils.SQLUtils;

/**
 * Created by Tobias on 09.06.2017.
 */

public class OwnFavoriteFragment extends Fragment implements OverviewTabContract.DatabaseView {

    private FragmentOverviewTabBinding bind;
    private Context context;
    private OverviewTabAdapter adapter;
    private OwnFavoritePresenter presenter;

    public static Fragment newInstance(int overviewType) {
        OwnFavoriteFragment ownFavoriteFragment = new OwnFavoriteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TYPE, overviewType);
        ownFavoriteFragment.setArguments(bundle);
        return ownFavoriteFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        presenter = new OwnFavoritePresenter(this, context);
        presenter.parseType(getArguments());
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
        adapter = new OverviewTabAdapter(bind.recyclerView, this);
        bind.recyclerView.setAdapter(adapter);
    }

    @Override
    public void setPosterItems(ArrayList<PosterOverviewItem> movies) {
        adapter.removeLoadingItem();
        adapter.setPosterItems(movies);
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
        presenter.onMovieClick(id);
    }

    @Override
    public void startDetailActivity(int id, int type) {
        Intent openMovieDetails = new Intent(context, DetailActivity.class);
        openMovieDetails.putExtra(Constants.CLICKED_MOVIE, id);
        openMovieDetails.putExtra(Constants.TYPE, type);
        startActivity(openMovieDetails);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return presenter.onCreateLoader(id);
    }

    @Override
    public Loader<Cursor> onCreateMovieLoader() {
        return new CursorLoader(
                context,
                MoviesContract.MovieEntry.CONTENT_URI,
                SQLUtils.selectAllMovies,
                null,
                null,
                null);
    }

    @Override
    public Loader<Cursor> onCreateSerieLoader() {
        return new CursorLoader(
                context,
                SeriesContract.SeriesEntry.CONTENT_URI,
                SQLUtils.selectAllSeries,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        presenter.onDatabaseLoadFinished(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.setPosterItems(null);
    }

    @Override
    public void startLoader(int loaderId) {
        getActivity().getSupportLoaderManager().restartLoader(loaderId, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(context).registerReceiver(movieInsertedIntoDatabase, new IntentFilter(Constants.MOVIE_INSERT_TO_DATABASE));
        LocalBroadcastManager.getInstance(context).registerReceiver(serieInsertedIntoDatabase, new IntentFilter(Constants.SERIE_INSERT_TO_DATABASE));
        setPosterItems(null);
        presenter.onResume();
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
