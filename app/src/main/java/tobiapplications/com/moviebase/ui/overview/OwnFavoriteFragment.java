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
    private static final int CURSOR_LOADER_ID = 123;


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
        presenter = new OwnFavoritePresenter(this, context);
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
    public void showNetworkError(boolean noNetwork) {
        if (bind.noInternetConnectionTextView != null && bind.recyclerView != null) {
            bind.recyclerView.setVisibility(noNetwork ? View.GONE : View.VISIBLE);
            bind.noInternetConnectionTextView.setVisibility(noNetwork ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void setMovies(ArrayList<MovieOverviewModel> movies) {
        adapter.removeLoadingItem();
        adapter.setMovies(movies);
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
    public int getCurrentMovieSize() {
        if (adapter != null) {
            return adapter.getItemCount();
        }
        return 0;
    }

    @Override
    public void onMovieClick(int id) {
        Intent openMovieDetails = new Intent(context, DetailActivity.class);
        openMovieDetails.putExtra(Constants.CLICKED_MOVIE, id);
        startActivity(openMovieDetails);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                context,
                MoviesContract.MovieEntry.CONTENT_URI,
                SQLUtils.selectAll,
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
        adapter.setMovies(null);
    }

    @Override
    public void startLoader() {
        getActivity().getSupportLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(context).registerReceiver(movieInsertedIntoDatabase, new IntentFilter(Constants.MOVIE_INSERT_TO_DATABASE));
        setMovies(null);
        presenter.loadMoviesFromDatabase();
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(movieInsertedIntoDatabase);
    }

    public void displayNoFavoriteMoviesText(boolean noFavoriteMovies) {
        bind.noFavoriteMovies.setVisibility(noFavoriteMovies ? View.VISIBLE : View.GONE);
    }

    private BroadcastReceiver movieInsertedIntoDatabase = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onResume();
        }
    };
}
