package andi.android.madegdk.ui.home.favorite.movie

import andi.android.madegdk.R
import andi.android.madegdk.model.Movie
import andi.android.madegdk.ui.home.favorite.movie.adapter.FavoriteMovieAdapter
import andi.android.madegdk.ui.home.movie.detail.MovieDetailActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_favorite_movie.view.*

class FavoriteMovieFragment : Fragment() {

    private lateinit var favoriteMovieView: View
    private val extraMovie = "EXTRA_MOVIE"

    private lateinit var favoriteMovieAdapter: FavoriteMovieAdapter
    private lateinit var favoriteMovieViewModel: FavoriteMovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        favoriteMovieView = inflater.inflate(R.layout.fragment_favorite_movie, container, false)

        favoriteMovieAdapter = FavoriteMovieAdapter(context){
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(extraMovie, it)
            startActivity(intent)
        }

        favoriteMovieView.favoriteMovieRV.layoutManager = GridLayoutManager(context, 3)
        favoriteMovieAdapter.notifyDataSetChanged()
        favoriteMovieView.favoriteMovieRV.adapter = favoriteMovieAdapter

        favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel::class.java)
        favoriteMovieViewModel.setMovies(context)
        favoriteMovieViewModel.getMovies().observe(this, getFavoriteMovies)

        return favoriteMovieView

    }

    private val getFavoriteMovies = Observer<ArrayList<Movie>> {
        favoriteMovieAdapter.setMovies(it)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.d("VISIBLE", "FavoriteMovieFragment")
        if (isVisibleToUser) {
            Log.d("VISIBLE", "FavoriteMovieFragment")
            favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel::class.java)
            favoriteMovieViewModel.setMovies(context)
            favoriteMovieViewModel.getMovies().observe(this, getFavoriteMovies)
            favoriteMovieAdapter.notifyDataSetChanged()
        }
    }

}