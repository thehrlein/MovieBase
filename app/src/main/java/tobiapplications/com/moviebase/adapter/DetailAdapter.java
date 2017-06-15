package tobiapplications.com.moviebase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.RecyclerItem;
import tobiapplications.com.moviebase.model.detail.views.InfoView;
import tobiapplications.com.moviebase.model.detail.views.SummaryView;
import tobiapplications.com.moviebase.viewholder.detail.InfoViewHolder;
import tobiapplications.com.moviebase.viewholder.detail.SummaryViewHolder;

/**
 * Created by Tobias on 14.06.2017.
 */

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<RecyclerItem> itemList;
    private LayoutInflater layoutInflater;

    public static final int VIEW_TYPE_INFO = 200;
    public static final int VIEW_TYPE_SUMMARY = 201;

    public DetailAdapter(Context context) {
        this.context = context;
        itemList = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_INFO:
                return new InfoViewHolder(LayoutInflater.from(context).inflate(R.layout.view_info, parent, false), context);
            case VIEW_TYPE_SUMMARY:
                return new SummaryViewHolder(LayoutInflater.from(context).inflate(R.layout.view_summary, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerItem item = itemList.get(position);
        switch (item.getItemType()) {
            case VIEW_TYPE_INFO:
                bindInfoView((InfoViewHolder) holder, item);
                break;
            case VIEW_TYPE_SUMMARY:
                bindSummaryView((SummaryViewHolder) holder, item);
        }
    }

    private void bindSummaryView(SummaryViewHolder summaryHolder, RecyclerItem item) {
        SummaryView summaryView = (SummaryView) item.getItem();
        summaryHolder.setSummary(summaryView.getSummary());
    }

    private void bindInfoView(InfoViewHolder infoHolder, RecyclerItem item) {
        InfoView infoView = (InfoView) item.getItem();
        infoHolder.setInformation(infoView);
    }

    @Override
    public int getItemCount() {
        if (itemList != null && !itemList.isEmpty()) {
            return itemList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList != null && !itemList.isEmpty()) {
            return itemList.get(position).getItemType();
        }

        return -1;
    }

    public void addUiViews(ArrayList<RecyclerItem> detailItems) {
        if (itemList != null) {
            itemList.addAll(detailItems);
            notifyItemRangeChanged(itemList.size() - detailItems.size(), itemList.size());
        }
    }
}
