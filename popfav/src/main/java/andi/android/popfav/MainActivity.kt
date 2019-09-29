package andi.android.popfav

import andi.android.popfav.database.DatabaseContract.FavoriteMoviesColumn.Companion.CONTENT_URI
import andi.android.popfav.detail.FavoriteDetailActivity
import andi.android.popfav.helper.mapFavoriteMovieCursorToArrayList
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity(), LoadFavoritesCallback {

    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var myObserver: DataObserver

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
        const val EXTRA_POSITION = "EXTRA_POSITION"
        const val REQUEST_FAVORITE = 888
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        favoriteAdapter = FavoriteAdapter(this) {movie, i ->
            val intent = Intent(this, FavoriteDetailActivity::class.java)
            val uri = Uri.parse("$CONTENT_URI/${favoriteAdapter.getFavorites()[i].movieId}")
            intent.data = uri
            intent.putExtra(EXTRA_MOVIE, movie)
            intent.putExtra(EXTRA_POSITION, i)
            startActivityForResult(intent, REQUEST_FAVORITE)
        }

        favoriteRV.layoutManager = LinearLayoutManager(this)
        favoriteRV.setHasFixedSize(true)
        favoriteRV.adapter = favoriteAdapter
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        myObserver = DataObserver(handler, this)
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
        GetData(this, this).execute()
    }

    override fun postExecute(favorites: Cursor) {
        val mFavorites = mapFavoriteMovieCursorToArrayList(favorites)
        if (mFavorites.size > 0) {
            favoriteAdapter.setFavorites(mFavorites)
        } else {
            favoriteAdapter.setFavorites(ArrayList())
        }
    }

    private class GetData(context: Context, callback: LoadFavoritesCallback) : AsyncTask<Void, Void, Cursor>() {
        private val weakContext: WeakReference<Context> = WeakReference(context)
        private val weakCallback: WeakReference<LoadFavoritesCallback> = WeakReference(callback)

        override fun doInBackground(vararg voids: Void): Cursor? {
            return weakContext.get()?.contentResolver?.query(CONTENT_URI, null, null, null, null)
        }

        override fun onPostExecute(data: Cursor) {
            super.onPostExecute(data)
            weakCallback.get()?.postExecute(data)
        }

    }

    internal class DataObserver(handler: Handler, private val context: Context) : ContentObserver(handler) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            GetData(context, context as MainActivity).execute()
        }
    }
}
