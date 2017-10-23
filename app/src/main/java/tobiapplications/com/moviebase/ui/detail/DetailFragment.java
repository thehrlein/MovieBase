package tobiapplications.com.moviebase.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Objects;

import tobiapplications.com.moviebase.adapter.DetailAdapter;
import tobiapplications.com.moviebase.databinding.FragmentDetailBinding;
import tobiapplications.com.moviebase.model.DisplayableItem;
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
    private Context context;

    public static DetailFragment newInstance(SeriesDetailResponse response, int overviewType) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.CLICKED_SERIE, response);
        bundle.putInt(Constants.TYPE, overviewType);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    public static DetailFragment newInstance(MovieDetailResponse response, int overviewType) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.CLICKED_MOVIE, response);
        bundle.putInt(Constants.TYPE, overviewType);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bind = FragmentDetailBinding.inflate(inflater);

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        presenter = new DetailFragmentPresenter(context, this, getArguments());
        presenter.attach(this);
        presenter.init();
    }

    @Override
    public void setAdapter(int overviewType) {
        adapter = new DetailAdapter(overviewType);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detach();
        }
    }
}