package andi.android.madegdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import andi.android.madegdk.model.Movie;
import andi.android.madegdk.utils.Util;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView titleTV, dateTV, overviewTV;
    private ImageView posterIV, posterBackgroundIV;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initUI();

        Movie movie = getIntent().getParcelableExtra(HomeActivity.EXTRA_MOVIE);
        titleTV.setText(movie.getTitle());
        dateTV.setText(movie.getDate());
        overviewTV.setText(movie.getOverview());
        ratingBar.setRating(Util.convertRatingToFloat(movie.getRating()));

        Util util = new Util(this);
        Glide.with(this)
                .load(util.getDrawableId(movie.getPoster()))
                .into(posterIV);
        Glide.with(this)
                .load(util.getDrawableId(movie.getPoster()))
                .into(posterBackgroundIV);
    }

    private void initUI() {
        titleTV = findViewById(R.id.titleTV);
        dateTV = findViewById(R.id.dateTV);
        overviewTV = findViewById(R.id.overviewTV);
        posterIV = findViewById(R.id.posterIV);
        posterBackgroundIV = findViewById(R.id.posterBackgroundIV);
        ratingBar = findViewById(R.id.ratingBar);
    }
}
