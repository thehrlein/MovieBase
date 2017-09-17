package tobiapplications.com.moviebase.adapter.delegates.movie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.detail.ActorsResponse;
import tobiapplications.com.moviebase.ui.viewholder.detail.movie.ActorsViewHolder;

/**
 * Created by Tobias on 06.09.2017.
 */

public class ActorsDelegate extends AdapterDelegate<List<DisplayableItem>> {

    private Context context;

    public ActorsDelegate(Context context) {
        this.context = context;
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof ActorsResponse;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ActorsViewHolder(LayoutInflater.from(context).inflate(R.layout.detail_actors_holder, parent, false), context);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ActorsResponse actorsResponse = (ActorsResponse) items.get(position);
        ActorsViewHolder actorsViewHolder = (ActorsViewHolder) holder;
        actorsViewHolder.setActorInformation(actorsResponse.getActors());
    }
}
