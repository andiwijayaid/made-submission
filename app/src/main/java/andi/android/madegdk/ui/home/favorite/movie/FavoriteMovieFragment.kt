package andi.android.madegdk.ui.home.favorite.movie

import andi.android.madegdk.R
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.CONTENT_URI
import andi.android.madegdk.helper.mapFavoriteMovieCursorToArrayList
import andi.android.madegdk.model.Movie
import andi.android.madegdk.provider.DataObserver
import andi.android.madegdk.ui.home.favorite.movie.adapter.FavoriteMovieAdapter
import andi.android.madegdk.ui.home.movie.detail.MovieDetailActivity
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite_movie.view.*
import java.lang.ref.WeakReference


class FavoriteMovieFragment : Fragment(), LoadFavoriteMoviesCallback {

    private lateinit var favoriteMovieView: View

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
        const val EXTRA_POSITION = "EXTRA_POSITION"
        const val EXTRA_IS_REMOVED = "IS_REMOVED"
        const val REQUEST_FAVORITE = 888
        const val EXTRA_STATE = "EXTRA_STATE"

        private lateinit var handlerThread: HandlerThread
    }

    private var mSavedInstanceState: Bundle? = null

    lateinit var favoriteMovieAdapter: FavoriteMovieAdapter
    private lateinit var myObserver: DataObserver

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        favoriteMovieView = inflater.inflate(R.layout.fragment_favorite_movie, container, false)

        if (savedInstanceState != null) {
            mSavedInstanceState = savedInstanceState
        }

        favoriteMovieAdapter = FavoriteMovieAdapter(context) { movie: Movie, position: Int ->
            val intent = Intent(context, MovieDetailActivity::class.java)
            val uri = Uri.parse("$CONTENT_URI/${favoriteMovieAdapter.getMovies()[position].movieId}")
            intent.data = uri
            intent.putExtra(EXTRA_MOVIE, movie)
            intent.putExtra(EXTRA_POSITION, position)
            startActivityForResult(intent, REQUEST_FAVORITE)
        }

        handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        myObserver = DataObserver(handler, context)
        context?.contentResolver?.registerContentObserver(CONTENT_URI, true, myObserver)

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

    override fun preExecute() {
        activity?.runOnUiThread {
            favoriteMovieView.progressBar.visibility = View.VISIBLE
        }
    }

    override fun postExecute(favoriteMovies: Cursor) {
        favoriteMovieView.progressBar.visibility = View.INVISIBLE
        val listFavoriteMovies = mapFavoriteMovieCursorToArrayList(favoriteMovies)
        if (listFavoriteMovies.size > 0) {
            favoriteMovieAdapter.setMovies(listFavoriteMovies)
        }
    }

    class LoadFavoriteMoviesAsync(context: Context?, callback: LoadFavoriteMoviesCallback) : AsyncTask<Void, Void, Cursor>() {
        private val weakContext = WeakReference(context)
        private val weakCallback = WeakReference(callback)

        override fun doInBackground(vararg params: Void?): Cursor? {
            val context = weakContext.get()
            return context?.contentResolver?.query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null)
        }

        override fun onPostExecute(result: Cursor) {
            super.onPostExecute(result)
            weakCallback.get()?.postExecute(result)
        }

        override fun onPreExecute() {
            super.onPreExecute()
            weakCallback.get()?.preExecute()
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
            context?.let { LoadFavoriteMoviesAsync(it, this).execute() }
        } else {
            val favoriteMovies = mSavedInstanceState!!.getParcelableArrayList<Movie>(EXTRA_STATE)
            if (favoriteMovies != null) {
                favoriteMovieAdapter.setMovies(favoriteMovies)
            }
        }
    }

}