package andi.android.madegdk.ui.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import andi.android.madegdk.R;
import andi.android.madegdk.model.Movie;
import andi.android.madegdk.utils.Util;

public class MovieAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<Movie> movies;

    public MovieAdapter(Context context) {
        this.context = context;
        movies = new ArrayList<>();
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(convertView);
        Movie movie = (Movie) getItem(position);
        viewHolder.bind(movie);
        return convertView;
    }

    private class ViewHolder {
        private TextView titleTV, dateTV, runtimeTV;
        private ImageView posterIV;
        private RatingBar ratingBar;

        ViewHolder(View view) {
            titleTV = view.findViewById(R.id.titleTV);
            dateTV = view.findViewById(R.id.dateTV);
            posterIV = view.findViewById(R.id.posterIV);
            ratingBar = view.findViewById(R.id.ratingBar);
            runtimeTV = view.findViewById(R.id.runtimeTV);
        }

        @SuppressLint("SetTextI18n")
        void bind(Movie movie) {
            titleTV.setText(movie.getTitle());
            dateTV.setText(movie.getDate());
            runtimeTV.setText(movie.getRuntime() + " min");
            Glide.with(context)
                    .load(getDrawableId(movie.getPoster()))
                    .into(posterIV);
            ratingBar.setRating(Util.convertRatingToFloat(movie.getRating()));
        }
    }

    private Integer getDrawableId(String drawableName) {
        return context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
    }
}