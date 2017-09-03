package tobiapplications.com.moviebase.ui.viewholder.detail;

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
import tobiapplications.com.moviebase.databinding.DetailAdditionalInfoHolderBinding;
import tobiapplications.com.moviebase.model.detail.Genre;
import tobiapplications.com.moviebase.model.detail.items.AdditionalInfoItem;
import tobiapplications.com.moviebase.ui.views.GenreTextView;

/**
 * Created by Tobias on 15.06.2017.
 */

public class AdditionalInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private DetailAdditionalInfoHolderBinding bind;
    private String url;
    private Context context;

    public AdditionalInfoViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        bind = DetailAdditionalInfoHolderBinding.bind(itemView);
    }

    public void setAdditionalInfo(AdditionalInfoItem additionalInfo) {
        bind.originalTitle.setText(additionalInfo.getOriginalTitle());
        bind.budget.setText(checkIfMoneyIsEmpty(additionalInfo.getBudget()));
        bind.revenue.setText(checkIfMoneyIsEmpty(additionalInfo.getRevenue()));
        if (TextUtils.isEmpty(additionalInfo.getHomepage())) {
            hideHomepageLine();
        } else {
            url = additionalInfo.getHomepage();
            bind.link.setOnClickListener(this);
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
        bind.link.setVisibility(View.GONE);
        bind.homepageLabel.setVisibility(View.GONE);
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
                bind.genres.addView(tvGenre);
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
