package andi.android.madegdk.ui.home.favorite.movie

import andi.android.madegdk.R
import andi.android.madegdk.database.FavoriteMovieHelper
import andi.android.madegdk.model.Movie
import andi.android.madegdk.ui.home.favorite.movie.adapter.FavoriteMovieAdapter
import andi.android.madegdk.ui.home.movie.detail.MovieDetailActivity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite_movie.view.*
import java.lang.ref.WeakReference

class FavoriteMovieFragment : Fragment(), LoadFavoriteMoviesCallback {
    override fun preExecute() {
        activity?.runOnUiThread { favoriteMovieView.progressBar.visibility = View.VISIBLE }
    }

    override fun postExecute(favoriteMovies: ArrayList<Movie>?) {
        favoriteMovieView.progressBar.visibility = View.INVISIBLE
        if (favoriteMovies != null) {
            favoriteMovieAdapter.setMovies(favoriteMovies)
//            setData(favoriteMovies)
        }
    }

    private lateinit var favoriteMovieView: View

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
        const val EXTRA_POSITION = "EXTRA_POSITION"
        const val EXTRA_IS_REMOVED = "IS_REMOVED"
        const val REQUEST_FAVORITE = 888
        const val EXTRA_STATE = "EXTRA_STATE"
    }

    lateinit var favoriteMovieAdapter: FavoriteMovieAdapter
    private var favoriteMovieHelper: FavoriteMovieHelper? = null
    private lateinit var favoriteMovies: ArrayList<Movie>
    private var mSavedInstanceState: Bundle? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        favoriteMovieHelper = FavoriteMovieHelper.getInstance(context)
        favoriteMovieHelper?.open()

        favoriteMovieView = inflater.inflate(R.layout.fragment_favorite_movie, container, false)

        favoriteMovieAdapter = FavoriteMovieAdapter(context) { movie: Movie, position: Int ->
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE, movie)
            intent.putExtra(EXTRA_POSITION, position)
            startActivityForResult(intent, REQUEST_FAVORITE)
        }

        favoriteMovieView.favoriteMovieRV.layoutManager = GridLayoutManager(context, 3)
        favoriteMovieAdapter.notifyDataSetChanged()
        favoriteMovieView.favoriteMovieRV.adapter = favoriteMovieAdapter

        setData()

        return favoriteMovieView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, favoriteMovieAdapter.getMovies())
    }

    private class LoadFavoriteMoviesAsync(favoriteMovieHelper: FavoriteMovieHelper?, callback: LoadFavoriteMoviesCallback) : AsyncTask<Void, Void, ArrayList<Movie>>() {
        private val weakFavoriteMovieHelper = WeakReference(favoriteMovieHelper)
        private val weakCallback = WeakReference(callback)

        override fun doInBackground(vararg params: Void?): ArrayList<Movie>? {
            return weakFavoriteMovieHelper.get()?.getAllFavoriteMovies()
        }

        override fun onPreExecute() {
            super.onPreExecute()
            weakCallback.get()?.preExecute()
        }

        override fun onPostExecute(result: ArrayList<Movie>?) {
            super.onPostExecute(result)
            weakCallback.get()?.postExecute(result)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val isRemoved = data.getBooleanExtra(EXTRA_IS_REMOVED, false)
            if (requestCode == REQUEST_FAVORITE && isRemoved) {
                val position = data.getIntExtra(EXTRA_POSITION, 0)
                favoriteMovieAdapter.deleteMovie(position)
            }
        }
    }

    fun setData() {
        if (mSavedInstanceState == null) {
            Log.d("ASD", "ASD")
            LoadFavoriteMoviesAsync(favoriteMovieHelper, this).execute()
        } else {
            Log.d("DSA", "DSA")
            favoriteMovies = mSavedInstanceState!!.getParcelableArrayList(EXTRA_STATE)
            favoriteMovieAdapter.setMovies(favoriteMovies)
        }
    }

}