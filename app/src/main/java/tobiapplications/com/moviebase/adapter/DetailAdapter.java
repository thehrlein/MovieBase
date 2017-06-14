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
import tobiapplications.com.moviebase.viewholder.detail.InfoViewHolder;

/**
 * Created by Tobias on 14.06.2017.
 */

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<RecyclerItem> itemList;
    private LayoutInflater layoutInflater;

    public static final int VIEW_TYPE_INFO = 200;

    public DetailAdapter(Context context) {
        this.context = context;
        itemList = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_INFO:
                View view = LayoutInflater.from(context).inflate(R.layout.view_info, parent, false);
                return new InfoViewHolder(view, context);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerItem item = itemList.get(position);
        switch (item.getItemType()) {
            case VIEW_TYPE_INFO:
                bindInfoView((InfoViewHolder) holder, item);
        }
    }

    private void bindInfoView(InfoViewHolder viewHolder, RecyclerItem item) {
        InfoView infoView = (InfoView) item.getItem();
        viewHolder.setInformation(infoView);
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
