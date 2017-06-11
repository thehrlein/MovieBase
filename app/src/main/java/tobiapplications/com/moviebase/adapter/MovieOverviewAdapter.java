package tobiapplications.com.moviebase.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.Movie;
import tobiapplications.com.moviebase.utils.RoundedTransformation;

/**
 * Created by Tobias on 09.06.2017.
 */

public class MovieOverviewAdapter extends RecyclerView.Adapter<MovieOverviewAdapter.MovieHolder> {

    private final RecyclerView mRecyclerView;
    private final Context mContext;
    private ArrayList<Movie> mMovies;

    public MovieOverviewAdapter(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;

        setRecyclerViewScrollListener();
    }

    private void setRecyclerViewScrollListener() {
        final GridLayoutManager gridLayoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = gridLayoutManager.getChildCount();
                int totalItemCount = gridLayoutManager.getItemCount();
                int pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    Log.d("A", "YO");
                }
            }
        });
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_item, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Movie movie = mMovies.get(position);
        holder.mMovieTitleNoPicture.setText(movie.getTitle());
        holder.mMovieTitle.setText(movie.getTitle());
        Picasso.with(mContext).load(movie.getTitleImagePath()).into(holder.mPosterImage);
    }

    @Override
    public int getItemCount() {
        if (mMovies != null) {
            return mMovies.size();
        }
        return 0;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.mMovies = movies;
    }

    class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        private ImageView mPosterImage;
        private TextView mMovieTitleNoPicture;
        private TextView mMovieTitle;
        private ImageView mMovieCardDots;

        public MovieHolder(View itemView) {
            super(itemView);

            mPosterImage = (ImageView) itemView.findViewById(R.id.movie_image);
            mMovieTitle = (TextView) itemView.findViewById(R.id.movie_title);
            mMovieTitleNoPicture = (TextView) itemView.findViewById(R.id.movie_title_no_picture);
            mMovieCardDots = (ImageView) itemView.findViewById(R.id.movie_card_dots);
            mMovieCardDots.setOnClickListener(this);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();

            if (id == R.id.movie_card_dots) {
                showPopMenu(v);
            } else {
                // TODO open details
            }
        }

        private void showPopMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(mContext, view);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.menu_movie_card, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.action_add_favorite) {
                Toast.makeText(mContext, "Add favorite", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    }
}
