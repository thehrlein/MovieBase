package tobiapplications.com.moviebase.ui.viewholder.detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import tobiapplications.com.moviebase.databinding.DetailSummaryHolderBinding;

/**
 * Created by Tobias on 15.06.2017.
 */

public class SummaryViewHolder extends RecyclerView.ViewHolder {

    private DetailSummaryHolderBinding bind;

    public SummaryViewHolder(View itemView) {
        super(itemView);
        bind = DetailSummaryHolderBinding.bind(itemView);
    }

    public void setSummary(String text) {
        bind.summaryText.setText(text);
    }
}