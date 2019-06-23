package andi.android.madegdk;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Objects;

import andi.android.madegdk.model.Movie;
import andi.android.madegdk.utils.Util;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView titleTV, dateTV, overviewTV, budgetTV, revenueTV;
    private ImageView posterIV, posterBackgroundIV;
    private RatingBar ratingBar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initUI();
        Util util = new Util(this);

        Movie movie = getIntent().getParcelableExtra(HomeActivity.EXTRA_MOVIE);
        titleTV.setText(movie.getTitle());
        dateTV.setText(movie.getDate());
        overviewTV.setText(movie.getOverview() + "\n");
        ratingBar.setRating(Util.convertRatingToFloat(movie.getRating()));

        if (util.isZero(movie.getBudget())) {
            budgetTV.setText(getString(R.string.not_available_sign));
        } else {
            budgetTV.setText(util.convertToCurrency(movie.getBudget()));
        }

        if (util.isZero(movie.getRevenue())) {
            revenueTV.setText(getString(R.string.not_available_sign));
        } else {
            revenueTV.setText(util.convertToCurrency(movie.getRevenue()));
        }

        Glide.with(this)
                .load(util.getDrawableId(movie.getPoster()))
                .into(posterIV);
        Glide.with(this)
                .load(util.getDrawableId(movie.getPoster()))
                .into(posterBackgroundIV);
    }

    private void initUI() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleTV = findViewById(R.id.titleTV);
        dateTV = findViewById(R.id.dateTV);
        overviewTV = findViewById(R.id.overviewTV);
        budgetTV = findViewById(R.id.budgetTV);
        revenueTV = findViewById(R.id.revenueTV);
        posterIV = findViewById(R.id.posterIV);
        posterBackgroundIV = findViewById(R.id.posterBackgroundIV);
        ratingBar = findViewById(R.id.ratingBar);

        Button watchBT = findViewById(R.id.watchBT);
        watchBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Watching...", Toast.LENGTH_LONG).show();
            }
        });
    }
}
