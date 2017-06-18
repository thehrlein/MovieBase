package tobiapplications.com.moviebase.viewholder.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.detail.Genre;
import tobiapplications.com.moviebase.model.detail.items.AdditionalInfoItem;
import tobiapplications.com.moviebase.ui.general_views.GenreTextView;

/**
 * Created by Tobias on 15.06.2017.
 */

public class AdditionalInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView originalTitle;
    private TextView budget;
    private TextView revenue;
    private TextView link;
    private TextView homepage;
    private FlexboxLayout genresLayout;
    private String url;
    private Context context;

    public AdditionalInfoViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        originalTitle = (TextView) itemView.findViewById(R.id.additional_info_origin_title);
        budget = (TextView) itemView.findViewById(R.id.additional_info_budget);
        revenue = (TextView) itemView.findViewById(R.id.additional_info_revenue);
        genresLayout = (FlexboxLayout) itemView.findViewById(R.id.additional_info_genres);
        homepage = (TextView) itemView.findViewById(R.id.homepage_label);
        link = (TextView) itemView.findViewById(R.id.additional_info_link);

    }

    public void setAdditionalInfo(AdditionalInfoItem additionalInfo) {
        originalTitle.setText(additionalInfo.getOriginalTitle());
        budget.setText(checkIfMoneyIsEmpty(additionalInfo.getBudget()));
        revenue.setText(checkIfMoneyIsEmpty(additionalInfo.getRevenue()));
        if (TextUtils.isEmpty(additionalInfo.getHomepage())) {
            hideHomepageLine();
        } else {
            url = additionalInfo.getHomepage();
            link.setOnClickListener(this);
        }
        setGenresLayout(additionalInfo.getGenres());
    }

    private String checkIfMoneyIsEmpty(int budget) {
        if (budget == 0) {
            return context.getString(R.string.unknown);
        }
        return formatMoney(budget);
    }

    private void hideHomepageLine() {
        link.setVisibility(View.GONE);
        homepage.setVisibility(View.GONE);
    }

    private String formatMoney(int budget) {
    return NumberFormat.getNumberInstance(Locale.GERMAN).format(budget) + " $";
    }

    private void setGenresLayout(ArrayList<Genre> genres) {
        if (genres != null && !genres.isEmpty()) {
            for (Genre genre : genres) {
                String name = genre.getName();
                GenreTextView tvGenre = new GenreTextView(context);
                tvGenre.setText(name);
                genresLayout.addView(tvGenre);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }
}
