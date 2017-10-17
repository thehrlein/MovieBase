package tobiapplications.com.moviebase.ui.viewholder.detail.serie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.DetailAdditionalSerieInfoHolderBinding;
import tobiapplications.com.moviebase.model.detail.Genre;
import tobiapplications.com.moviebase.model.detail.items.serie.AdditionalSerieInfoItem;
import tobiapplications.com.moviebase.ui.views.GenreTextView;

/**
 * Created by Tobias on 17.09.2017.
 */

public class AdditionalSerieInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private DetailAdditionalSerieInfoHolderBinding bind;
    private Context context;
    private String url;

    public AdditionalSerieInfoViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        bind = DetailAdditionalSerieInfoHolderBinding.bind(itemView);
    }

    public void setAdditionalInfo(AdditionalSerieInfoItem additionalInfo) {
        bind.originalTitle.setText(additionalInfo.getOriginalTitle());
        bind.episodes.setText(additionalInfo.getEpisodes());
        bind.seasons.setText(additionalInfo.getSeasons());
        if (TextUtils.isEmpty(additionalInfo.getHomepage())) {
            hideHomepageLine();
        } else {
            url = additionalInfo.getHomepage();
            bind.link.setOnClickListener(this);
        }
        setGenresLayout(additionalInfo.getGenres());
    }

    private void hideHomepageLine() {
        bind.homepageLayout.setVisibility(View.GONE);
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
        Intent openHomepage = new Intent(Intent.ACTION_VIEW);
        openHomepage.setData(Uri.parse(url));
        context.startActivity(openHomepage);
    }
}
