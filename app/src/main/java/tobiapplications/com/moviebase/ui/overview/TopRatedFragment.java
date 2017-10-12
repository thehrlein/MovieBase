package tobiapplications.com.moviebase.ui.overview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tobiapplications.com.moviebase.adapter.OverviewTabAdapter;
import tobiapplications.com.moviebase.databinding.FragmentOverviewTabBinding;
import tobiapplications.com.moviebase.model.overview.PosterOverviewItem;
import tobiapplications.com.moviebase.ui.detail.DetailActivity;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.GeneralUtils;

/**
 * Created by Tobias on 09.06.2017.
 */

public class TopRatedFragment extends Fragment implements OverviewTabFragmentContract.View {

    private final String TAG = TopRatedFragment.class.getSimpleName();
    private FragmentOverviewTabBinding bind;
    private Context context;
    private TopRatedPresenter presenter;
    private OverviewTabAdapter adapter;
    private int overviewType;

    public static Fragment newInstance(int overviewType) {
        TopRatedFragment topRatedFragment = new TopRatedFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.OVERVIEW_TYPE, overviewType);
        topRatedFragment.setArguments(bundle);
        return topRatedFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentOverviewTabBinding.inflate(inflater);
        presenter = new TopRatedPresenter(this, context);
        overviewType = getOverviewType(getArguments());
        presenter.load(overviewType);
        return bind.getRoot();
    }

    private int getOverviewType(Bundle arguments) {
        if (arguments == null) {
            return -1;
        }

        if (arguments.containsKey(Constants.OVERVIEW_TYPE)) {
            return arguments.getInt(Constants.OVERVIEW_TYPE);
        }

        return -1;
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
        adapter.setOnLoadMoreMoviesListener(this);
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
    public void setMovies(ArrayList<PosterOverviewItem> movies) {
        adapter.removeLoadingItem();
        adapter.setPosterItems(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading(boolean load) {
        bind.progressBarLoading.setVisibility(load ? View.VISIBLE : View.GONE);
        bind.recyclerView.setVisibility(load ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getCurrentMovieSize() {
        if (adapter != null) {
            return adapter.getItemCount();
        }
        return 0;
    }

    @Override
    public void insertLoadingItem() {
        adapter.insertLoadingItem();
    }

    @Override
    public void loadMoreMovies() {
        presenter.loadMore();
    }

    @Override
    public void onMovieClick(int id) {
        Intent openMovieDetails = new Intent(context, DetailActivity.class);
        openMovieDetails.putExtra(Constants.CLICKED_MOVIE, id);
        openMovieDetails.putExtra(Constants.OVERVIEW_TYPE, overviewType);
        startActivity(openMovieDetails);
    }
}
