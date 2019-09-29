package andi.android.madegdk.ui.home.releasetoday

import andi.android.madegdk.R
import andi.android.madegdk.database.DatabaseContract
import andi.android.madegdk.model.Movie
import andi.android.madegdk.ui.home.movie.MovieFragment
import andi.android.madegdk.ui.home.movie.adapter.MovieAdapter
import andi.android.madegdk.ui.home.movie.detail.MovieDetailActivity
import andi.android.madegdk.utils.getCurrentDate
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

class ReleaseTodayActivity : AppCompatActivity() {

    private lateinit var movieAdapter: MovieAdapter

    private lateinit var movieViewModel: ReleaseTodayMovieViewModel

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
            movieViewModel.setMovies(resources.getString(R.string.language_code), getCurrentDate())
            showLoading(true)
        }
        movieViewModel.getMovies().observe(this, getMovies)
    }

    private val getMovies = Observer<ArrayList<Movie>> { movies ->
        this.movies = movies
        showLoading(false)
        if (movies != null) {
            movieAdapter.setMovies(movies)
        } else {
            Toast.makeText(this, resources.getString(R.string.check_your_connection), Toast.LENGTH_LONG).show()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}