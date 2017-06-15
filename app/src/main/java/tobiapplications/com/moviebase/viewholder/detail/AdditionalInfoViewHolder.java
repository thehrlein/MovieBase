package tobiapplications.com.moviebase.viewholder.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.detail.Genre;
import tobiapplications.com.moviebase.model.detail.views.AdditionalInfoView;
import tobiapplications.com.moviebase.model.detail.views.GenreTextView;

/**
 * Created by Tobias on 15.06.2017.
 */

public class AdditionalInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView originalTitle;
    private TextView budget;
    private TextView revenue;
    private TextView link;
    private FlexboxLayout genresLayout;
    private String homepage;
    private Context context;

    public AdditionalInfoViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        originalTitle = (TextView) itemView.findViewById(R.id.additional_info_origin_title);
        budget = (TextView) itemView.findViewById(R.id.additional_info_budget);
        revenue = (TextView) itemView.findViewById(R.id.additional_info_revenue);
        genresLayout = (FlexboxLayout) itemView.findViewById(R.id.additional_info_genres);
        link = (TextView) itemView.findViewById(R.id.additional_info_link);
        link.setOnClickListener(this);
    }

    public void setAdditionalInfo(AdditionalInfoView additionalInfo) {
        originalTitle.setText(additionalInfo.getOriginalTitle());
        budget.setText(formatMoney(additionalInfo.getBudget()));
        revenue.setText(formatMoney(additionalInfo.getRevenue()));
        homepage = additionalInfo.getHomepage();
        setGenresLayout(additionalInfo.getGenres());
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
        i.setData(Uri.parse(homepage));
        context.startActivity(i);
    }
}
