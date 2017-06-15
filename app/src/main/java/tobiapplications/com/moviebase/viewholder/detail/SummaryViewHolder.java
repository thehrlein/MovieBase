package tobiapplications.com.moviebase.viewholder.detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tobiapplications.com.moviebase.R;

/**
 * Created by Tobias on 15.06.2017.
 */

public class SummaryViewHolder extends RecyclerView.ViewHolder {

    private TextView summaryText;

    public SummaryViewHolder(View itemView) {
        super(itemView);
        summaryText = (TextView) itemView.findViewById(R.id.summary_text);
    }

    public void setSummary(String text) {
        summaryText.setText(text);
    }
}