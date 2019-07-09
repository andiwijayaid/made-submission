package andi.android.madegdk.ui.home.movie

import andi.android.madegdk.R
import andi.android.madegdk.model.Movie
import andi.android.madegdk.ui.home.movie.adapter.MovieAdapter
import andi.android.madegdk.ui.home.movie.detail.MovieDetailActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_movie.view.*
import java.util.*

class MovieFragment : Fragment() {

    private lateinit var movieAdapter: MovieAdapter
    private val extraMovie = "EXTRA_MOVIE"

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieView: View
    private var page = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        movieView = inflater.inflate(R.layout.fragment_movie, container, false)

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        movieViewModel.setMovies(resources.getString(R.string.language_code), page)
        showLoading(true)
        movieViewModel.getMovies().observe(this, getMovies)

        movieAdapter = MovieAdapter(context) {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(extraMovie, it)
            startActivity(intent)
        }
        movieAdapter.notifyDataSetChanged()
        movieView.movieRV?.adapter = movieAdapter
        movieView.movieRV?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

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

    private fun showLoading(state: Boolean) {
        if (state) {
            movieView.progressBar.visibility = View.VISIBLE
        } else {
            movieView.progressBar.visibility = View.GONE
        }
    }

    private val getMovies = Observer<ArrayList<Movie>> { movies ->
        if (movies != null) {
            if (page == 1) {
                movieAdapter.setMovies(movies)
            } else {
                movieAdapter.addMovies(movies)
            }
            showLoading(false)
            movieView.refreshLayout.finishRefresh(true)
            movieView.refreshLayout.finishLoadMore(true)
        } else {
            showLoading(true)
        }
    }
}