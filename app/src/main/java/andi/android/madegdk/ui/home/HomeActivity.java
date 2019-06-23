package andi.android.madegdk.ui.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

import andi.android.madegdk.R;
import andi.android.madegdk.model.Movie;
import andi.android.madegdk.model.MovieCollection;
import andi.android.madegdk.ui.home.adapter.MovieAdapter;
import andi.android.madegdk.ui.moviedetail.MovieDetailActivity;

public class HomeActivity extends AppCompatActivity {

    private MovieCollection movieCollection;
    private MovieAdapter movieAdapter;
    static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);

        movieAdapter = new MovieAdapter(this);
        final ListView listView = findViewById(R.id.movieLV);
        listView.setAdapter(movieAdapter);

        readJson();
        initMovies();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CardView posterCV = view.findViewById(R.id.posterCV);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View, String>(posterCV, getString(R.string.poster));
                    ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                            HomeActivity.this, pairs
                    );
                    Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                    intent.putExtra(EXTRA_MOVIE, movieCollection.getMovies().get(position));
                    startActivity(intent, activityOptions.toBundle());
                } else {
                    Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                    intent.putExtra(EXTRA_MOVIE, movieCollection.getMovies().get(position));
                    startActivity(intent);
                }
            }
        });
    }

    private void initMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieCollection.getMovies().size(); i++) {
            Movie movie = new Movie(
                    movieCollection.getMovies().get(i).getPoster(),
                    movieCollection.getMovies().get(i).getTitle(),
                    movieCollection.getMovies().get(i).getDate(),
                    movieCollection.getMovies().get(i).getRating(),
                    movieCollection.getMovies().get(i).getRuntime(),
                    movieCollection.getMovies().get(i).getBudget(),
                    movieCollection.getMovies().get(i).getRevenue(),
                    movieCollection.getMovies().get(i).getOverview()
            );
            movies.add(movie);
        }
        movieAdapter.setMovies(movies);
    }

    private void readJson() {
        String jsonString;

        try {
            InputStream inputStream = getAssets().open("movie.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            jsonString = new String(buffer, StandardCharsets.UTF_8);

            Gson gson = new Gson();
            movieCollection = gson.fromJson(jsonString, MovieCollection.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
