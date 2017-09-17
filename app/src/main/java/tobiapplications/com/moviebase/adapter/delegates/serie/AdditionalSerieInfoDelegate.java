package tobiapplications.com.moviebase.adapter.delegates.serie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.DisplayableItem;
import tobiapplications.com.moviebase.model.detail.items.serie.AdditionalSerieInfoItem;
import tobiapplications.com.moviebase.ui.viewholder.detail.serie.AdditionalSerieInfoViewHolder;

/**
 * Created by Tobias on 17.09.2017.
 */

public class AdditionalSerieInfoDelegate extends AdapterDelegate<List<DisplayableItem>> {

    private Context context;

    public AdditionalSerieInfoDelegate(Context context) {
        this.context = context;
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof AdditionalSerieInfoItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new AdditionalSerieInfoViewHolder(LayoutInflater.from(context).inflate(R.layout.detail_additional_serie_info_holder, parent, false), context);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        AdditionalSerieInfoViewHolder serieInfoViewHolder = (AdditionalSerieInfoViewHolder) holder;

        AdditionalSerieInfoItem infoItem = (AdditionalSerieInfoItem) items.get(position);
        serieInfoViewHolder.setAdditionalInfo(infoItem);
    }
}
