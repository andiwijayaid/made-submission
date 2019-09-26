package andi.android.madegdk.ui.home.search.movie

import andi.android.madegdk.R
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.CONTENT_URI
import andi.android.madegdk.model.Movie
import andi.android.madegdk.ui.home.HomeActivity
import andi.android.madegdk.ui.home.movie.MovieFragment.Companion.extraMovie
import andi.android.madegdk.ui.home.movie.adapter.MovieAdapter
import andi.android.madegdk.ui.home.movie.detail.MovieDetailActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search_movie.*
import kotlinx.android.synthetic.main.fragment_search_movie.view.*
import kotlinx.android.synthetic.main.fragment_search_movie.view.notFoundLL

class SearchMovieFragment : Fragment(), HomeActivity.MovieSearchListener {
    override fun sendQueryToSearchMovieFragment(query: String?) {
        Log.d("QUERY M", query)
        this.query = query.toString()
        searchMovieViewModel.setMovies(resources.getString(R.string.language_code), query)
        showLoading(true)
        searchMovieViewModel.getMovies().observe(this, getMovies)
    }

    private lateinit var movieAdapter: MovieAdapter

    private lateinit var searchMovieViewModel: SearchMovieViewModel
    private lateinit var searchMovieView: View

    private lateinit var movies: ArrayList<Movie>

    private lateinit var query: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        searchMovieView = inflater.inflate(R.layout.fragment_search_movie, container, false)

        (activity as HomeActivity).setMovieQueryListener(this)

        movieAdapter = MovieAdapter(context) { movie: Movie, position: Int ->
            val intent = Intent(context, MovieDetailActivity::class.java)
            val uri = Uri.parse("$CONTENT_URI/${movieAdapter.getMovies()[position].movieId}")
            intent.data = uri
            intent.putExtra(extraMovie, movie)
            startActivity(intent)
        }

        movieAdapter.notifyDataSetChanged()
        searchMovieView.movieRV?.adapter = movieAdapter
        searchMovieView.movieRV?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        searchMovieViewModel = ViewModelProviders.of(this).get(SearchMovieViewModel::class.java)

        searchMovieView.refreshLayout.setOnRefreshListener {
            searchMovieViewModel.setMovies(resources.getString(R.string.language_code), query)
        }

        return searchMovieView
    }

    private val getMovies = Observer<ArrayList<Movie>> { movies ->
        this.movies = movies
        showLoading(false)
        searchMovieView.refreshLayout.finishRefresh(true)
        searchMovieView.refreshLayout.finishLoadMore(true)
        if (movies != null) {
            if (movies.size == 0) {
                searchMovieView.notFoundLL.visibility = View.VISIBLE
            } else {
                notFoundLL.visibility = View.GONE
                movieAdapter.setMovies(movies)
            }
            searchMovieView.onFailLL.visibility = View.GONE
        } else {
            searchMovieView.onFailLL.visibility = View.VISIBLE
            Toast.makeText(context, resources.getString(R.string.check_your_connection), Toast.LENGTH_LONG).show()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            searchMovieView.progressBar.visibility = View.VISIBLE
        } else {
            searchMovieView.progressBar.visibility = View.GONE
        }
    }

}