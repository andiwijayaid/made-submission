package andi.android.madegdk.provider

import andi.android.madegdk.database.DatabaseContract
import andi.android.madegdk.database.DatabaseContract.Companion.AUTHORITY
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.TABLE_FAVORITE_MOVIE
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.TABLE_FAVORITE_TV_SERIES
import andi.android.madegdk.database.FavoriteMovieHelper
import andi.android.madegdk.database.FavoriteTvSeriesHelper
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.util.Log

class FavoriteProvider : ContentProvider() {

    companion object {
        private const val FAVORITE_MOVIE = 1
        private const val FAVORITE_MOVIE_ID = 2

        private const val FAVORITE_TV_SERIES = 11
        private const val FAVORITE_TV_SERIES_ID = 12

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_MOVIE, FAVORITE_MOVIE)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_FAVORITE_MOVIE/#", FAVORITE_MOVIE_ID)

            sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_TV_SERIES, FAVORITE_TV_SERIES)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_FAVORITE_TV_SERIES/#", FAVORITE_TV_SERIES_ID)
        }
    }

    private var favoriteMovieHelper: FavoriteMovieHelper? = null
    private var favoriteTvSeriesHelper: FavoriteTvSeriesHelper? = null

    override fun onCreate(): Boolean {
        favoriteMovieHelper = FavoriteMovieHelper.getInstance(context)
        favoriteTvSeriesHelper = FavoriteTvSeriesHelper.getInstance(context)
        return true
    }

    override fun query(uri: Uri?, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        favoriteMovieHelper?.open()
        favoriteTvSeriesHelper?.open()
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            FAVORITE_MOVIE -> cursor = favoriteMovieHelper?.queryProvider()
            FAVORITE_MOVIE_ID -> cursor = uri?.lastPathSegment?.let { favoriteMovieHelper?.queryByIdProvider(it) }
            FAVORITE_TV_SERIES -> cursor = favoriteTvSeriesHelper?.queryProvider()
            FAVORITE_TV_SERIES_ID -> cursor = uri?.lastPathSegment?.let { favoriteTvSeriesHelper?.queryByIdProvider(it) }
            else -> cursor = null
        }
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        favoriteMovieHelper?.open()
        favoriteTvSeriesHelper?.open()
        val added: Long?
        if (sUriMatcher.match(uri) == FAVORITE_MOVIE) {
            added = values?.let { favoriteMovieHelper?.insertProvider(it) }
//            context?.contentResolver?.notifyChange(DatabaseContract.FavoriteMoviesColumn.CONTENT_URI, DataObserver(Handler(), context))
            return Uri.parse("${DatabaseContract.FavoriteMoviesColumn.CONTENT_URI}/$added")
        } else if (sUriMatcher.match(uri) == FAVORITE_TV_SERIES) {
            added = values?.let { favoriteTvSeriesHelper?.insertProvider(it) }
            context?.contentResolver?.notifyChange(DatabaseContract.FavoriteTvSeriesColumn.CONTENT_URI, DataObserver(Handler(), context))
            return Uri.parse("${DatabaseContract.FavoriteTvSeriesColumn.CONTENT_URI}/$added")
        }
        return Uri.parse("${DatabaseContract.FavoriteMoviesColumn.CONTENT_URI}/0")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        favoriteMovieHelper?.open()
        favoriteTvSeriesHelper?.open()
        val updated = when (sUriMatcher.match(uri)) {
            FAVORITE_MOVIE_ID -> favoriteMovieHelper?.updateProvider(uri.lastPathSegment, values)
            FAVORITE_TV_SERIES_ID -> favoriteTvSeriesHelper!!.updateProvider(uri.lastPathSegment, values)
            else -> 0
        }
//        context?.contentResolver?.notifyChange(CONTENT_URI, FavoriteMovieFragment.DataObserver(Handler(), context))
        return updated!!
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        favoriteMovieHelper?.open()
        favoriteTvSeriesHelper?.open()
        val deleted: Int?
        if (sUriMatcher.match(uri) == FAVORITE_MOVIE_ID) {
            deleted = favoriteMovieHelper?.deleteProvider(uri.lastPathSegment)
            return deleted!!
        } else if (sUriMatcher.match(uri) == FAVORITE_TV_SERIES_ID) {
            deleted = favoriteTvSeriesHelper!!.deleteProvider(uri.lastPathSegment)
            return deleted!!
        }
        return 0
    }
}