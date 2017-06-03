package me.johngummadi.movies.views.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.johngummadi.movies.R;
import me.johngummadi.movies.models.Movie;

/**
 * Created by johngummadi on 6/1/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    /**
     * NOTE: that I am using custom listener interface.
     * Otherwise we'll have to use View.OnClickListener which
     * is little tricky for the users of this adapter to understand.
     * This just simplifies things.
     * TODO: See if there are better ways
     */
    public interface OnItemClickListener {
        void onClick(int pos, View itemView);
    }

    List<Movie> _data;
    OnItemClickListener _itemClickListener;

    public MovieAdapter(List<Movie> data, MovieAdapter.OnItemClickListener itemClickListener) {
        _data = data;
        _itemClickListener = itemClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        Movie movie = _data.get(position);

        // NOTE: In tablet we're not showing poster image in the list.
        if (holder._ivPosterThumb != null) {
            Glide.with(holder.itemView.getContext())
                    .load(movie.getPosterPathFull())
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_movie)
                    .into(holder._ivPosterThumb);
        }

        holder._tvMovieTitle.setText(movie.getTitle());
        holder._tvMovieOverview.setText(movie.getOverview());
        holder._rbMovieRating.setRating((float) movie.getVoteAverage()/2);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pos = holder.getAdapterPosition();
                MovieAdapter.this.notifyItemChanged(pos, "payload " + pos);
                if (_itemClickListener != null) {
                    _itemClickListener.onClick(pos, v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        @Nullable @BindView(R.id.ivPosterThumb) ImageView _ivPosterThumb;
        @BindView(R.id.tvMovieTitle) TextView _tvMovieTitle;
        @BindView(R.id.tvMovieOverview) TextView _tvMovieOverview;
        @BindView(R.id.rbMovieRating) RatingBar _rbMovieRating;

        public MovieViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
