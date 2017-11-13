package tobiapplications.com.moviebase.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tobiapplications.com.moviebase.adapter.SearchMovieAdapter;
import tobiapplications.com.moviebase.databinding.FragmentSearchBinding;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.ui.detail.DetailActivity;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 25.06.2017.
 */

public class SearchResultsFragment extends Fragment implements SearchResultsContract.View {

    private FragmentSearchBinding bind;
    private SearchMovieAdapter searchMovieAdapter;
    private SearchResultsPresenter presenter;
    private Context context;

    public static SearchResultsFragment newInstance(Bundle bundle) {
        SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
        searchResultsFragment.setArguments(bundle);
        return searchResultsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchResultsPresenter(this);
        presenter.attach(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentSearchBinding.inflate(inflater);

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        presenter.init(getArguments(), context);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void setTitle(String title) {
        getActivity().setTitle(title);
    }

    @Override
    public void setAdapter() {
        bind.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        searchMovieAdapter = new SearchMovieAdapter(this);
        bind.recyclerView.setAdapter(searchMovieAdapter);
    }

    @Override
    public void setSearchMovies(ArrayList<DisplayableItem> movies) {
        searchMovieAdapter.setSearchMovies(movies);
    }

    @Override
    public void setDownloadIsActive(){
        bind.progressBarLoading.setVisibility(View.VISIBLE);
        bind.recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDownloadFinished() {
        bind.progressBarLoading.setVisibility(View.GONE);
        bind.recyclerView.setVisibility(View.VISIBLE);
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
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detach();
        }
    }
}
