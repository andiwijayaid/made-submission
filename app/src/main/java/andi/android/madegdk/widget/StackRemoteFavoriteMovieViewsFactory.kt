package andi.android.madegdk.widget

import andi.android.madegdk.BuildConfig
import andi.android.madegdk.R
import andi.android.madegdk.database.FavoriteMovieHelper
import andi.android.madegdk.helper.mapFavoriteMovieCursorToArrayList
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class StackRemoteFavoriteMovieViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = arrayListOf<Bitmap>()

    override fun onCreate() {}

    private fun getBitmapFromURL(src: String): Bitmap? {
        return try {
            val url = URL(src)
            val connection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            // Log exception
            null
        }
    }

    override fun onDataSetChanged() {
        val favoriteMovieHelper = FavoriteMovieHelper.getInstance(mContext)
        val favoriteMovieCursor = favoriteMovieHelper?.queryProvider()
        val favoriteMovies = mapFavoriteMovieCursorToArrayList(favoriteMovieCursor)
        if (favoriteMovies.size > 0) {
            Log.d("AS", "SA")
            for (i in 0 until favoriteMovies.size) {
                getBitmapFromURL("${BuildConfig.IMAGE_URL}t/p/w185${favoriteMovies[i].poster}")?.let { mWidgetItems.add(it) }
                Log.d("URL", favoriteMovies[i].poster)
            }
        }
    }

    override fun onDestroy() {}

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(mContext.packageName, R.layout.widget_favorite_movie_item)
        remoteViews.setImageViewBitmap(
                R.id.imageView,
                mWidgetItems[position]
        )

        val extras = Bundle()
        extras.putInt(mContext.packageName, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return remoteViews
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}