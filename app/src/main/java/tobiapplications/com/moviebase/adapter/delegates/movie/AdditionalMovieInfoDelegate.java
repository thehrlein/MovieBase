package tobiapplications.com.moviebase.adapter.delegates.movie;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.detail.items.movie.AdditionalMovieInfoItem;
import tobiapplications.com.moviebase.ui.viewholder.detail.movie.AdditionalMovieInfoViewHolder;

/**
 * Created by Tobias on 06.09.2017.
 */

public class AdditionalMovieInfoDelegate extends AdapterDelegate<List<DisplayableItem>> {

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof AdditionalMovieInfoItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new AdditionalMovieInfoViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_additional_movie_info_holder, parent, false), parent.getContext());
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        AdditionalMovieInfoItem additionalMovieInfoItem = (AdditionalMovieInfoItem) items.get(position);
        AdditionalMovieInfoViewHolder additionalMovieInfoViewHolder = (AdditionalMovieInfoViewHolder) holder;
        additionalMovieInfoViewHolder.setAdditionalInfo(additionalMovieInfoItem);
    }
}