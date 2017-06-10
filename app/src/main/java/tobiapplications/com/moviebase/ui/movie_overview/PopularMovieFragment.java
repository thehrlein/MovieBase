package tobiapplications.com.moviebase.ui.movie_overview;

import android.content.Context;
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
import android.widget.Toast;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.adapter.MovieOverviewAdapter;
import tobiapplications.com.moviebase.model.Movie;
import tobiapplications.com.moviebase.utils.Helper;

/**
 * Created by Tobias on 09.06.2017.
 */

public class PopularMovieFragment extends Fragment implements MovieOverview{

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBarLoading;
    private TextView mNoInternetConnectionTextView;
    private Context context;
    private PopularMoviePresenter presenter;
    private MovieOverviewAdapter adapter;

    public static Fragment newInstance() {
        PopularMovieFragment popularMovieFragment = new PopularMovieFragment();

        return popularMovieFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        presenter = new PopularMoviePresenter(this, context);
        presenter.loadMovies();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_overview_fragment, container, false);
    }

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
        adapter = new MovieOverviewAdapter(context);
        int howMuchColumns = Helper.getHowMuchColumnsForMovies(context);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, howMuchColumns));
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNoNetworkError(boolean isConnected) {
        mNoInternetConnectionTextView.setVisibility(isConnected ? View.GONE : View.VISIBLE);
        mRecyclerView.setVisibility(isConnected ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setMovies(ArrayList<Movie> movies) {
        adapter.setMovies(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading(boolean load) {
        mProgressBarLoading.setVisibility(load ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(load ? View.GONE : View.VISIBLE);
    }
}
