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

import java.util.ArrayList;

import tobiapplications.com.moviebase.adapter.OverviewTabAdapter;
import tobiapplications.com.moviebase.databinding.FragmentOverviewTabBinding;
import tobiapplications.com.moviebase.model.overview.PosterOverviewItem;
import tobiapplications.com.moviebase.ui.detail.DetailActivity;
import tobiapplications.com.moviebase.utils.Constants;
import tobiapplications.com.moviebase.utils.GeneralUtils;

/**
 * Created by Tobias Hehrlein on 14.10.2017.
 */

public class BaseTabFragment extends Fragment implements BaseTabContract.View {

    private FragmentOverviewTabBinding bind;
    private OverviewTabAdapter adapter;
    private Context context;
    private BaseTabPresenter presenter;
    private GridLayoutManager gridLayoutManager;
    private int currentCounter = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.context = getContext();
        bind = FragmentOverviewTabBinding.inflate(inflater);

        return bind.getRoot();
    }

    @Override
    public void setPresenter(BaseTabPresenter presenter) {
        this.presenter = presenter;
        this.presenter.attach(this);
    }

    @Override
    public void setGridViewAndAdapter() {
        int howMuchColumns = GeneralUtils.getHowMuchColumnsForOverviewMovies(context);
        gridLayoutManager = new GridLayoutManager(context, howMuchColumns);
        bind.recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new OverviewTabAdapter(bind.recyclerView, this);
        adapter.setOnLoadMoreMoviesListener(this);
        bind.recyclerView.setAdapter(adapter);
    }

    @Override
    public void initCounter() {
        setCounterTotal();
        bind.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                updateCounter(dy > 0);
                updateScrollUpButton(dy >= 0);
            }
        });
    }

    private void updateScrollUpButton(boolean scrollDown) {
        if (gridLayoutManager == null) {
            return;
        }

        if (gridLayoutManager.findFirstVisibleItemPosition() < 5) {
            bind.scrollUpButton.hide();
        } else if (scrollDown) {
            bind.scrollUpButton.hide();
        } else {
            bind.scrollUpButton.show();
        }
    }

    private void setCounterTotal() {
        if (adapter == null) {
            bind.counterLayout.setVisibility(View.GONE);
            return;
        }

        int totalItems = adapter.getMoviePosterCount();
        if (totalItems == 0) {
            bind.counterLayout.setVisibility(View.GONE);
            return;
        }

        bind.counterLayout.setVisibility(View.VISIBLE);
        bind.counterTotal.setText(String.valueOf(adapter.getMoviePosterCount()));
    }

    private void updateCounter(boolean scrollDown) {
        int offSet = scrollDown ? GeneralUtils.pxFromDp(context, 5) : -bind.counterLayout.getHeight();
        int counterLayoutMarginTop = bind.counterLayout.getTop() - offSet;
        int counter = getCurrentVisiblePosterItem(counterLayoutMarginTop);

        if (counter != currentCounter) {
            currentCounter = counter;
            bind.counter.setText(String.valueOf(counter));
            setCounterTotal();
        }
    }

    private int getCurrentVisiblePosterItem(int counterLayoutMarginTop) {
        int pos = gridLayoutManager.findFirstVisibleItemPosition();
        View firstVisibleView = gridLayoutManager.findViewByPosition(pos);
        int addition;
        if ((pos == adapter.getItemCount() - 2 || pos == adapter.getItemCount() - 1) && adapter.getItemCount() % 2 != 0) {
            addition = 1;
        } else {
            addition = 2;
        }

        if (firstVisibleView == null) {
            return pos;
        }
        if (firstVisibleView.getBottom() < counterLayoutMarginTop) {
            pos += addition;
        }

        return pos + addition;
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
        if (adapter == null) {
            return 0;
        }
        return adapter.getItemCount();
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
        presenter.onMovieClick(id);
    }

    @Override
    public void initFabScrollUpButton() {
        bind.scrollUpButton.setOnClickListener(v -> scrollSmoothToTop());
    }

    private void scrollSmoothToTop() {
        bind.recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void startDetailActivity(int id, int type) {
        Intent openMovieDetails = new Intent(context, DetailActivity.class);
        openMovieDetails.putExtra(Constants.CLICKED_MOVIE, id);
        openMovieDetails.putExtra(Constants.TYPE, type);
        startActivity(openMovieDetails);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detach();
        }
        gridLayoutManager = null;
    }
}
