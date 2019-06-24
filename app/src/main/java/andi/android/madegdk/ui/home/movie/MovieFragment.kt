package andi.android.madegdk.ui.home.movie

import andi.android.madegdk.R
import andi.android.madegdk.model.Movie
import andi.android.madegdk.model.MovieCollection
import andi.android.madegdk.ui.home.movie.adapter.MovieAdapter
import andi.android.madegdk.ui.moviedetail.MovieDetailActivity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_movie.view.*
import kotlinx.android.synthetic.main.item_movie.*
import java.io.IOException
import java.nio.charset.StandardCharsets

class MovieFragment : Fragment() {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieCollection: MovieCollection
    private var movies: ArrayList<Movie> = arrayListOf()
    private val extraMovie = "EXTRA_MOVIE"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_movie, container, false)

        readJson()
        initMovies()

        movieAdapter = MovieAdapter(context, movies) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val pairs: Array<android.util.Pair<View, String>?> = arrayOfNulls(1)
                pairs[0] = android.util.Pair<View, String>(posterCV, getString(R.string.poster))
                val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                        activity, *pairs
                )
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra(extraMovie, it)
                startActivity(intent, activityOptions.toBundle())
            } else {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra(extraMovie, it)
                startActivity(intent)
            }
        }
        view.movieRV.adapter = movieAdapter
        view.movieRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return view
    }

    private fun initMovies() {
        movies.clear()
        for (i in 0 until movieCollection.movies!!.size) {
            val movie = Movie (
                    movieCollection.movies!![i].poster,
                    movieCollection.movies!![i].title,
                    movieCollection.movies!![i].date,
                    movieCollection.movies!![i].rating,
                    movieCollection.movies!![i].runtime,
                    movieCollection.movies!![i].budget,
                    movieCollection.movies!![i].revenue,
                    movieCollection.movies!![i].overview
            )
            movies.add(movie)
        }
    }

    private fun readJson() {
        val jsonString: String

        try {
            val inputStream = context?.getAssets()?.open("movie.json")
            val size = inputStream?.available()
            val buffer = ByteArray(size!!)
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