package tobiapplications.com.moviebase.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.SearchMovieAdapter;
import tobiapplications.com.moviebase.databinding.FragmentSearchBinding;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.ui.detail.DetailActivity;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 25.06.2017.
 */

public class SearchFragment extends Fragment implements SearchFragmentContract.View {

    private FragmentSearchBinding bind;
    private SearchMovieAdapter searchMovieAdapter;
    private SearchFragmentPresenter presenter;
    private Context context;

    public SearchFragment() {

    }

    public static SearchFragment newInstance(Bundle bundle) {
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.setArguments(bundle);
        return searchFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SearchFragmentPresenter(this);
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
        presenter.init(getArguments().getString(Constants.SEARCH_QUERY), context);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getArguments().getString(Constants.SEARCH_QUERY));
    }


    @Override
    public void setAdapter() {
        bind.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        searchMovieAdapter = new SearchMovieAdapter(context);

        bind.recyclerView.setAdapter(searchMovieAdapter);
        searchMovieAdapter.setMovieClickListener(this);
    }

    @Override
    public void setSearchMovies(ArrayList<MovieOverviewModel> movies) {
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
        Intent openMovieDetails = new Intent(context, DetailActivity.class);
        openMovieDetails.putExtra(Constants.CLICKED_MOVIE, id);
        startActivity(openMovieDetails);
    }
}
