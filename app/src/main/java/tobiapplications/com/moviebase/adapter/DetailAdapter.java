package tobiapplications.com.moviebase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.RecyclerItem;
import tobiapplications.com.moviebase.viewholder.detail.InfoViewHolder;

/**
 * Created by Tobias on 14.06.2017.
 */

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<RecyclerItem> itemList;

    public DetailAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new InfoViewHolder(LayoutInflater.from(context).inflate(R.layout.view_info, parent, false), context);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 1:
                bindInfoView((InfoViewHolder) holder, position);
        }
    }

    private void bindInfoView(InfoViewHolder viewHolder, int position) {
        RecyclerItem item = itemList.get(position);

    //    viewHolder.setInformation();
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
