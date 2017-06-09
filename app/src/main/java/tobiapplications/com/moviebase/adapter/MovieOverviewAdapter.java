package tobiapplications.com.moviebase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.model.Movie;

/**
 * Created by Tobias on 09.06.2017.
 */

public class MovieOverviewAdapter extends RecyclerView.Adapter<MovieOverviewAdapter.MovieHolder> {

    private Context mContext;
    private ArrayList<Movie> mMovies;

    public MovieOverviewAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_item, parent, false);
        MovieHolder movieHolder = new MovieHolder(view);
        return movieHolder;
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Movie movie = mMovies.get(position);
        holder.mMovieTitle.setText(movie.getTitle());
        Picasso.with(mContext).load(movie.getTitleImagePath()).into(holder.mPosterImage);

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.mMovies = movies;
    }

    class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mPosterImage;
        private TextView mMovieTitle;

        public MovieHolder(View itemView) {
            super(itemView);

            mPosterImage = (ImageView) itemView.findViewById(R.id.movie_poster_cardview);
            mMovieTitle = (TextView) itemView.findViewById(R.id.movie_title_no_picture);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
