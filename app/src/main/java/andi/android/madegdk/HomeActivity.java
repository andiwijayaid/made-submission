package andi.android.madegdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

import andi.android.madegdk.adapter.MovieAdapter;
import andi.android.madegdk.model.Movie;
import andi.android.madegdk.model.MovieCollection;

public class HomeActivity extends AppCompatActivity {

    private MovieCollection movieCollection;
    private MovieAdapter movieAdapter;

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

//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int lastFirstVisibleItem = 0;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view.getId() == listView.getId()) {
                    final int currentFirstVisibleItem = listView.getFirstVisiblePosition();
                    if (currentFirstVisibleItem > lastFirstVisibleItem) {
                        getSupportActionBar();
                    } else if (currentFirstVisibleItem < lastFirstVisibleItem) {
                        Objects.requireNonNull(getSupportActionBar()).show();
                    }
                    lastFirstVisibleItem = currentFirstVisibleItem;
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
