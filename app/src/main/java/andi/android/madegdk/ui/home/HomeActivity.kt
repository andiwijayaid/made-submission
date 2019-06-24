package andi.android.madegdk.ui.home

import andi.android.madegdk.R
import andi.android.madegdk.model.Movie
import andi.android.madegdk.model.MovieCollection
import andi.android.madegdk.ui.home.adapter.MovieAdapter
import andi.android.madegdk.ui.moviedetail.MovieDetailActivity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Pair
import android.view.View
import android.widget.AdapterView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_movie.*
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

class HomeActivity : AppCompatActivity() {

    private var movieCollection: MovieCollection? = null
    private var movieAdapter: MovieAdapter? = null
    private val extraMovie = "EXTRA_MOVIE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.elevation = 0f

        movieAdapter = MovieAdapter(this)
        movieLV.adapter = movieAdapter

        readJson()
        initMovies()

        movieLV.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val pairs = arrayOfNulls<Pair<View, String>>(1)
                pairs[0] = Pair<View, String>(posterCV, getString(R.string.poster))
                val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                        this@HomeActivity, *pairs
                )
                val intent = Intent(applicationContext, MovieDetailActivity::class.java)
                intent.putExtra(extraMovie, movieCollection?.movies?.get(position))
                startActivity(intent, activityOptions.toBundle())
            } else {
                val intent = Intent(applicationContext, MovieDetailActivity::class.java)
                intent.putExtra(extraMovie, movieCollection?.movies?.get(position))
                startActivity(intent)
            }
        }
    }

    private fun initMovies() {
        val movies = ArrayList<Movie>()
        for (i in 0 until movieCollection?.movies!!.size) {
            val movie = Movie(
                    movieCollection?.movies?.get(i)?.poster,
                    movieCollection?.movies?.get(i)?.title,
                    movieCollection?.movies?.get(i)?.date,
                    movieCollection?.movies?.get(i)?.rating,
                    movieCollection?.movies?.get(i)?.runtime,
                    movieCollection?.movies?.get(i)?.budget,
                    movieCollection?.movies?.get(i)?.revenue,
                    movieCollection?.movies?.get(i)?.overview
            )
            movies.add(movie)
        }
        movieAdapter?.setMovies(movies)
    }

    private fun readJson() {
        val jsonString: String

        try {
            val inputStream = assets.open("movie.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            jsonString = String(buffer, StandardCharsets.UTF_8)

            val gson = Gson()
            movieCollection = gson.fromJson(jsonString, MovieCollection::class.java)

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}