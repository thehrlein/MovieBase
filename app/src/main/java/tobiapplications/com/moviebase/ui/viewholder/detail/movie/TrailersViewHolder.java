package tobiapplications.com.moviebase.ui.viewholder.detail.movie;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.DetailTrailersHolderBinding;
import tobiapplications.com.moviebase.model.detail.items.movie.TrailerItem;
import tobiapplications.com.moviebase.ui.views.TrailerView;

/**
 * Created by Tobias on 16.06.2017.
 */

public class TrailersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private DetailTrailersHolderBinding bind;
    private Context context;
    private int shownTrailers = 3;
    private ArrayList<TrailerView> inflatedTrailerItems;

    public TrailersViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;

        bind = DetailTrailersHolderBinding.bind(itemView);
        bind.showNextTrailers.setOnClickListener(this);
    }

    public void setTrailers(ArrayList<TrailerItem> trailerItems) {
        inflatedTrailerItems = new ArrayList<>();

        for (int i = 0; i < trailerItems.size(); i++) {
            TrailerView view = new TrailerView(context);
            view.setTrailerInformation(trailerItems.get(i));
            bind.trailerLayout.addView(view);
            inflatedTrailerItems.add(view);

            if (i > 2) {
                view.setVisibility(View.GONE);
            }
        }

        if (inflatedTrailerItems.size() < shownTrailers) {
            shownTrailers = inflatedTrailerItems.size();
        }
        inflatedTrailerItems.get(shownTrailers - 1).hideDivider();
        hideLoadMoreTextIfLastTrailerIsShown();
    }

    private void hideLoadMoreTextIfLastTrailerIsShown() {
        if (shownTrailers == inflatedTrailerItems.size()) {
            bind.showNextTrailers.setVisibility(View.GONE);
        } else {
            bind.showNextTrailers.setText(context.getString(R.string.trailers_show_more, inflatedTrailerItems.size() - shownTrailers));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.show_next_trailers) {
            for (int i = 0; i < 3; i++) {
                if (shownTrailers < inflatedTrailerItems.size()) {
                    inflatedTrailerItems.get(shownTrailers - 1).showDivider();
                    inflatedTrailerItems.get(shownTrailers).setVisibility(View.VISIBLE);
                    shownTrailers++;
                    hideLoadMoreTextIfLastTrailerIsShown();
                }
            }
            inflatedTrailerItems.get(shownTrailers - 1).hideDivider();
        }
    }
}
