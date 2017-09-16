package tobiapplications.com.moviebase.adapter.delegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.detail.items.SerieInfoItem;
import tobiapplications.com.moviebase.ui.viewholder.detail.SerieInfoHolder;

/**
 * Created by Tobias on 16.09.2017.
 */

public class SerieInfoDelegate extends AdapterDelegate<List<DisplayableItem>> {

    private Context context;

    public SerieInfoDelegate(Context context) {
        this.context = context;
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof SerieInfoItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new SerieInfoHolder(LayoutInflater.from(context).inflate(R.layout.detail_serie_info_holder, parent, false), context);

    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        SerieInfoHolder serieInfoHolder = (SerieInfoHolder) holder;
        SerieInfoItem serieInfoItem = (SerieInfoItem) items.get(position);
        serieInfoHolder.setInformation(serieInfoItem);
    }
}
