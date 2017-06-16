package tobiapplications.com.moviebase.ui.search;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.SearchMovieAdapter;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.ui.detail.DetailActivity;
import tobiapplications.com.moviebase.utils.Constants;

public class SearchActivity extends AppCompatActivity implements SearchContract.View {

    private SearchMovieAdapter searchMovieAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBarLoading;
    private SearchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        findMyViews();

        presenter = new SearchPresenter(this, this);
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            setTitle("\"" + query + "\"");
            presenter.init(query);
        }
    }

    private void findMyViews() {
        mProgressBarLoading = (ProgressBar) findViewById(R.id.progressBarLoading);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    @Override
    public void setAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchMovieAdapter = new SearchMovieAdapter(this);

        mRecyclerView.setAdapter(searchMovieAdapter);
        searchMovieAdapter.setMovieClickListener(this);
    }

    @Override
    public void setSearchMovies(ArrayList<MovieOverviewModel> movies) {
        searchMovieAdapter.setSearchMovies(movies);
    }

    @Override
    public void setDownloadIsActive(){
        mProgressBarLoading.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDownloadFinished() {
        mProgressBarLoading.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMovieClick(int id) {
        Intent openMovieDetails = new Intent(this, DetailActivity.class);
        openMovieDetails.putExtra(Constants.CLICKED_MOVIE, id);
        startActivity(openMovieDetails);
    }
}
