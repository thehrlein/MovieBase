package tobiapplications.com.moviebase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.listener.OnMovieClickListener;
import tobiapplications.com.moviebase.model.overview.MovieOverviewModel;
import tobiapplications.com.moviebase.ui.viewholder.SearchMovieViewHolder;

/**
 * Created by Tobias on 01.04.2017.
 */

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieViewHolder> {

    private ArrayList<MovieOverviewModel> movies;
    private OnMovieClickListener mClickListener;
    private Context mContext;
    private LayoutInflater mInflater;

    public SearchMovieAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public SearchMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.search_list_item, parent, false);
        SearchMovieViewHolder searchMovieViewHolder = new SearchMovieViewHolder(view, mContext, mClickListener);
        return searchMovieViewHolder;
    }

    @Override
    public void onBindViewHolder(SearchMovieViewHolder holder, int position) {
        MovieOverviewModel movie = movies.get(position);
        holder.setSearchMovieInformation(movie);
    }

    public void setMovieClickListener(OnMovieClickListener movieClickListener) {
        mClickListener = movieClickListener;
    }

    @Override
    public int getItemCount() {
        if (movies != null) {
            return movies.size();
        }

        return 0;
    }

    public void setSearchMovies(ArrayList<MovieOverviewModel> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
}
