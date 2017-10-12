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

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.SearchMovieAdapter;
import tobiapplications.com.moviebase.databinding.FragmentSearchBinding;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.ui.detail.DetailActivity;
import tobiapplications.com.moviebase.utils.Constants;

/**
 * Created by Tobias on 25.06.2017.
 */

public class SearchResultsFragment extends Fragment implements SearchFragmentContract.View {

    private FragmentSearchBinding bind;
    private SearchMovieAdapter searchMovieAdapter;
    private SearchFragmentPresenter presenter;
    private Context context;
    private int overviewType;
    private String searchQuery;

    public static SearchResultsFragment newInstance(Bundle bundle) {
        SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
        searchResultsFragment.setArguments(bundle);
        return searchResultsFragment;
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
        parseArguments(getArguments());
        presenter.init(searchQuery, overviewType, context);
    }

    private void parseArguments(Bundle arguments) {
        if (arguments == null) {
            return;
        }

        if (arguments.containsKey(Constants.SEARCH_QUERY)) {
            searchQuery = arguments.getString(Constants.SEARCH_QUERY);
        }
        if (arguments.containsKey(Constants.TYPE)) {
            overviewType = arguments.getInt(Constants.TYPE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getTitle());
    }

    private String getTitle() {
        String title;
        String appending = ": " + searchQuery;
        if (overviewType == Constants.Type.MOVIES) {
            title = context.getString(R.string.movie_title);
        } else {
            title = context.getString(R.string.series_title);
        }

        title += appending;
        return title;
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
        Intent openMovieDetails = new Intent(context, DetailActivity.class);
        openMovieDetails.putExtra(Constants.CLICKED_MOVIE, id);
        openMovieDetails.putExtra(Constants.TYPE, overviewType);
        startActivity(openMovieDetails);
    }
}
