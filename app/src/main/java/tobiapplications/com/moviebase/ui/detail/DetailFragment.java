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
import tobiapplications.com.moviebase.model.RecyclerItem;
import tobiapplications.com.moviebase.model.detail.MovieDetailResponse;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 11.06.2017.
 */

public class DetailFragment extends Fragment implements DetailFragmentContract.View{

    private RecyclerView recyclerView;
    private DetailAdapter adapter;
    private DetailFragmentPresenter presenter;

    private Context mContext;
    private MovieDetailResponse detailMovie;

    public static DetailFragment newInstance(MovieDetailResponse response) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.CLICKED_MOVIE, response);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailMovie = (MovieDetailResponse) getArguments().get(Constants.CLICKED_MOVIE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        adapter = new DetailAdapter(mContext);
        presenter = new DetailFragmentPresenter(mContext, this);
        findMyViews();
        setAdapter();
        presenter.buildUiFromResponse(detailMovie);
        presenter.requestMovieDownload();
        presenter.requestReviews();
        presenter.requestActors();
    }

    private void findMyViews() {
        if (getView() != null) {
          recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        }
    }

    public void setAdapter() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void displayUiViews(ArrayList<RecyclerItem> detailItems) {
        adapter.addUiViews(detailItems);
    }

    @Override
    public void displayUiView(RecyclerItem item) {
        adapter.addUiView(item);
    }

    @Override
    public void onClick(View v) {

    }
}
