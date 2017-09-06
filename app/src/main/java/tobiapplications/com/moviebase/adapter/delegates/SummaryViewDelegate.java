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
import tobiapplications.com.moviebase.model.detail.items.SummaryItem;
import tobiapplications.com.moviebase.ui.viewholder.detail.SummaryViewHolder;

/**
 * Created by Tobias on 06.09.2017.
 */

public class SummaryViewDelegate extends AdapterDelegate<List<DisplayableItem>> {

    private Context context;

    public SummaryViewDelegate(Context context) {
        this.context = context;
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof SummaryItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new SummaryViewHolder(LayoutInflater.from(context).inflate(R.layout.detail_summary_holder, parent, false));

    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        SummaryItem summaryItem = (SummaryItem) items.get(position);
        SummaryViewHolder summaryViewHolder = (SummaryViewHolder) holder;
        summaryViewHolder.setSummary(summaryItem.getSummary());
    }
}
