package andi.android.madegdk.ui.home.movie

import andi.android.madegdk.R
import andi.android.madegdk.model.Movie
import andi.android.madegdk.ui.home.movie.adapter.MovieAdapter
import andi.android.madegdk.ui.home.movie.detail.MovieDetailActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.view.*

class MovieFragment : Fragment() {

    private lateinit var movieAdapter: MovieAdapter
    private val extraMovie = "EXTRA_MOVIE"

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieView: View
    private var page = 1

    private lateinit var movies: ArrayList<Movie>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        movieView = inflater.inflate(R.layout.fragment_movie, container, false)

        movieAdapter = MovieAdapter(context) {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(extraMovie, it)
            startActivity(intent)
        }
        movieAdapter.notifyDataSetChanged()
        movieView.movieRV?.adapter = movieAdapter
        movieView.movieRV?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        if (movieViewModel.countRetrievedMovies() == null) {
            movieViewModel.setMovies(resources.getString(R.string.language_code), page)
            showLoading(true)
        }
        movieViewModel.getMovies().observe(this, getMovies)

        movieView.refreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                page += 1
                movieViewModel.setMovies(resources.getString(R.string.language_code), page)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                page = 1
                movieViewModel.setMovies(resources.getString(R.string.language_code), page)
            }
        })

        return movieView
    }

    private val getMovies = Observer<ArrayList<Movie>> { movies ->
        this.movies = movies
        showLoading(false)
        movieView.refreshLayout.finishRefresh(true)
        movieView.refreshLayout.finishLoadMore(true)
        if (movies != null) {
            if (page == 1) {
                movieAdapter.setMovies(movies)
            } else {
                movieAdapter.addMovies(movies)
            }
            onFailLL.visibility = View.GONE
        } else {
            onFailLL.visibility = View.VISIBLE
            Toast.makeText(context, resources.getString(R.string.check_your_connection), Toast.LENGTH_LONG).show()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            movieView.progressBar.visibility = View.VISIBLE
        } else {
            movieView.progressBar.visibility = View.GONE
        }
    }
}