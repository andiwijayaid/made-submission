package andi.android.madegdk.ui.home.releasetoday

import andi.android.madegdk.R
import andi.android.madegdk.database.DatabaseContract
import andi.android.madegdk.model.Movie
import andi.android.madegdk.ui.home.movie.MovieFragment
import andi.android.madegdk.ui.home.movie.MovieViewModel
import andi.android.madegdk.ui.home.movie.adapter.MovieAdapter
import andi.android.madegdk.ui.home.movie.detail.MovieDetailActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_release_today.*
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.view.*

class ReleaseTodayActivity : AppCompatActivity() {

    private lateinit var movieAdapter: MovieAdapter

    companion object {
        const val extraMovie = "EXTRA_MOVIE"
    }

    private lateinit var movieViewModel: ReleaseTodayMovieViewModel
    private var page = 1

    private lateinit var movies: ArrayList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_release_today)

        movieAdapter = MovieAdapter(this) { movie: Movie, position: Int ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            val uri = Uri.parse("${DatabaseContract.FavoriteMoviesColumn.CONTENT_URI}/${movieAdapter.getMovies()[position].movieId}")
            intent.data = uri
            intent.putExtra(MovieFragment.extraMovie, movie)
            startActivity(intent)
        }

        movieAdapter.notifyDataSetChanged()
        todayReleaseRV?.adapter = movieAdapter
        todayReleaseRV?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        movieViewModel = ViewModelProviders.of(this).get(ReleaseTodayMovieViewModel::class.java)
        if (movieViewModel.countRetrievedMovies() == null) {
            movieViewModel.setMovies(resources.getString(R.string.language_code), "2019-09-28")
//            showLoading(true)
        }
        movieViewModel.getMovies().observe(this, getMovies)
    }

    private val getMovies = Observer<ArrayList<Movie>> { movies ->
        this.movies = movies
        if (movies != null) {
            movieAdapter.setMovies(movies)
//            onFailLL.visibility = View.GONE
        } else {
//            onFailLL.visibility = View.VISIBLE
            Toast.makeText(this, resources.getString(R.string.check_your_connection), Toast.LENGTH_LONG).show()
        }
    }
}