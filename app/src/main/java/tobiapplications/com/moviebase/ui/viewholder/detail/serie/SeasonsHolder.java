package tobiapplications.com.moviebase.ui.viewholder.detail.serie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;

import tobiapplications.com.moviebase.databinding.DetailSeasonsHolderBinding;
import tobiapplications.com.moviebase.listener.OnImageClickListener;
import tobiapplications.com.moviebase.model.detail.Season;
import tobiapplications.com.moviebase.ui.views.SeasonsView;
import tobiapplications.com.moviebase.utils.GeneralUtils;
import tobiapplications.com.moviebase.utils.NetworkUtils;

/**
 * Created by Tobias on 20.09.2017.
 */

public class SeasonsHolder extends RecyclerView.ViewHolder implements OnImageClickListener {

    private DetailSeasonsHolderBinding bind;
    private Context context;
    private ArrayList<Season> seasons;

    public SeasonsHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        bind = DetailSeasonsHolderBinding.bind(itemView);
    }


    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
        bind.table.removeAllViews();
        int count = 0;
        TableRow tableRow = null;
        for (int i = 0; i < seasons.size(); i++) {
            Season season = seasons.get(i);
            SeasonsView seasonsView = new SeasonsView(context);
            seasonsView.setSeason(season);
            seasonsView.setOnImageClickListener(this, i);
            seasonsView.setLayoutParams(getSeasonParams());
            if (count % 2 == 0) {
                tableRow = new TableRow(context);
                tableRow.setLayoutParams(getTableRowParams());
                tableRow.setWeightSum(1f);
                tableRow.addView(seasonsView);
                if (i == seasons.size() - 1) {
                    bind.table.addView(tableRow);
                }
            } else {
                if (tableRow != null) {
                    tableRow.addView(seasonsView);
                    bind.table.addView(tableRow);
                }
            }
            count++;
        }
    }


    private TableRow.LayoutParams getTableRowParams() {
        return new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private TableRow.LayoutParams getSeasonParams() {
        return new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.5f);
    }

    @Override
    public void onImageClick(int position) {
        onSeasonImageClick(position);
    }

    private void onSeasonImageClick(int clickedSeason) {
        if (TextUtils.isEmpty(seasons.get(clickedSeason).getPosterPath())) {
            return;
        }

        int howMuchWithImage = getHowMuchSeasonsWithImage();
        boolean fullListWithAndWithOutPictures[] = new boolean[seasons.size()];


        int howMuchWithOutImage = 0;
        String[] actorImages = new String[howMuchWithImage];
        for (int i = 0; i < seasons.size(); i++) {
            String movieImagePath = seasons.get(i).getPosterPath();
            if (!TextUtils.isEmpty(movieImagePath)) {
                movieImagePath = NetworkUtils.getFullImageUrlHigh(movieImagePath);
                actorImages[i - howMuchWithOutImage] = movieImagePath;
                fullListWithAndWithOutPictures[i] = true;
            } else {
                howMuchWithOutImage++;
                fullListWithAndWithOutPictures[i] = false;
            }
        }

        int goBackCounter = GeneralUtils.getGoBackCounter(clickedSeason, fullListWithAndWithOutPictures);
        int startPosition = clickedSeason - goBackCounter;
        startImagesSlideShow(actorImages, startPosition);
    }

    private int getHowMuchSeasonsWithImage() {
        int howMuchWithImage = 0;

        for (int i = 0; i < seasons.size(); i++) {
            if (!TextUtils.isEmpty(seasons.get(i).getPosterPath())) {
                howMuchWithImage++;
            }
        }

        return howMuchWithImage;
    }

    private void startImagesSlideShow(String[] seasonImages, int startPosition) {
        new ImageViewer.Builder(context, seasonImages)
                .setStartPosition(startPosition)
                .show();
    }
}
