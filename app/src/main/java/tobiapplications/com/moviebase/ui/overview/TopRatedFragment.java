package tobiapplications.com.moviebase.ui.overview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.OverviewAdapter;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.ui.detail.DetailActivity;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.RecyclerListUtils;

/**
 * Created by Tobias on 09.06.2017.
 */

public class TopRatedFragment extends Fragment implements OverviewFragmentContract.View {

    private final String TAG = TopRatedFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBarLoading;
    private TextView mNoInternetConnectionTextView;
    private Context context;
    private TopRatedPresenter presenter;
    private OverviewAdapter adapter;


    public static Fragment newInstance() {
        return new TopRatedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        presenter = new TopRatedPresenter(this, context);
        presenter.loadMovies();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findMyViews();
        setGridViewAndAdapter();
    }

    @Override
    public void findMyViews() {
        if (getView() != null) {
            mProgressBarLoading = (ProgressBar) getView().findViewById(R.id.progressBarLoading);
            mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
            mNoInternetConnectionTextView = (TextView) getView().findViewById(R.id.noInternetConnectionTextView);
        }
    }

    @Override
    public void setGridViewAndAdapter() {
        int howMuchColumns = RecyclerListUtils.getHowMuchColumnsForMovies(context);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(context, howMuchColumns);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new OverviewAdapter(context, mRecyclerView, TAG);
        adapter.setOnLoadMoreMoviesListener(this);
        adapter.setOnMovieClickListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showNetworkError(boolean noNetwork) {
        if (mNoInternetConnectionTextView != null && mRecyclerView != null) {
            mRecyclerView.setVisibility(noNetwork ? View.GONE : View.VISIBLE);
            mNoInternetConnectionTextView.setVisibility(noNetwork ? View.VISIBLE : View.GONE);
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
        if (mProgressBarLoading != null && mRecyclerView != null) {
            mProgressBarLoading.setVisibility(load ? View.VISIBLE : View.GONE);
            mRecyclerView.setVisibility(load ? View.GONE : View.VISIBLE);
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
    public void insertLoadingItem() {
        adapter.insertLoadingItem();
    }

    @Override
    public void loadMoreMovies() {
        presenter.loadMoreMovies();
    }

    @Override
    public void onMovieClick(int id) {
        Intent openMovieDetails = new Intent(context, DetailActivity.class);
        openMovieDetails.putExtra(Constants.CLICKED_MOVIE, id);
        startActivity(openMovieDetails);
    }
}
