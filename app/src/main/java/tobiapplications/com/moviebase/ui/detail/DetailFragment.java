package tobiapplications.com.moviebase.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.DetailAdapter;
import tobiapplications.com.moviebase.databinding.FragmentDetailBinding;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.RecyclerItem;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.model.detail.SeriesDetailResponse;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 11.06.2017.
 */

public class DetailFragment extends Fragment implements DetailFragmentContract.View{

    private FragmentDetailBinding bind;
    private DetailAdapter adapter;
    private DetailFragmentPresenter presenter;
    private Constants.OverviewType overviewType;
    private Context mContext;
    private MovieDetailResponse detailMovie;
    private SeriesDetailResponse detailSerie;

    public static DetailFragment newInstance(MovieDetailResponse response, Constants.OverviewType overviewType) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.CLICKED_MOVIE, response);
        bundle.putSerializable(Constants.OVERVIEW_TYPE, overviewType);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    public static DetailFragment newInstance(SeriesDetailResponse response, Constants.OverviewType overviewType) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.CLICKED_SERIE, response);
        bundle.putSerializable(Constants.OVERVIEW_TYPE, overviewType);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parseArguments(getArguments());
    }

    private void parseArguments(Bundle arguments) {
        overviewType = (Constants.OverviewType) arguments.get(Constants.OVERVIEW_TYPE);
        if (overviewType == Constants.OverviewType.MOVIES) {
            detailMovie = (MovieDetailResponse) arguments.get(Constants.CLICKED_MOVIE);
        } else {
            detailSerie = (SeriesDetailResponse) arguments.get(Constants.CLICKED_SERIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentDetailBinding.inflate(inflater);

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        adapter = new DetailAdapter(mContext);
        presenter = new DetailFragmentPresenter(mContext, this, overviewType);

        if (overviewType == Constants.OverviewType.MOVIES) {
            presenter.init(detailMovie);
        } else {
            presenter.init(detailSerie);
        }
        setAdapter();
    }

    public void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        bind.recyclerView.setLayoutManager(layoutManager);
        bind.recyclerView.setAdapter(adapter);
    }

    @Override
    public void displayUiViews(ArrayList<DisplayableItem> detailItems) {
        adapter.addUiViews(detailItems);
    }

    @Override
    public void displayUiView(DisplayableItem item) {
        adapter.addUiView(item);
    }
}
