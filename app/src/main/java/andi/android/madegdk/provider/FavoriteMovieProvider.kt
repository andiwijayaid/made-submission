package andi.android.madegdk.provider

import andi.android.madegdk.database.DatabaseContract.Companion.AUTHORITY
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.CONTENT_URI
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.TABLE_FAVORITE_MOVIE
import andi.android.madegdk.database.FavoriteMovieHelper
import andi.android.madegdk.ui.home.favorite.movie.FavoriteMovieFragment
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.os.Handler

class FavoriteMovieProvider : ContentProvider() {

    companion object {
        private const val FAVORITE_MOVIE = 1
        private const val FAVORITE_MOVIE_ID = 2

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_MOVIE, FAVORITE_MOVIE)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_FAVORITE_MOVIE/#", FAVORITE_MOVIE_ID)
        }
    }

    private var favoriteMovieHelper: FavoriteMovieHelper? = null

    override fun onCreate(): Boolean {
        favoriteMovieHelper = FavoriteMovieHelper.getInstance(context)
        return true
    }

    override fun query(uri: Uri?, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        favoriteMovieHelper?.open()
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            FAVORITE_MOVIE -> cursor = favoriteMovieHelper?.queryProvider()
            FAVORITE_MOVIE_ID -> cursor = uri?.lastPathSegment?.let { favoriteMovieHelper?.queryByIdProvider(it) }
            else -> cursor = null
        }
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        favoriteMovieHelper?.open()
        val added = when(sUriMatcher.match(uri)) {
            FAVORITE_MOVIE -> values?.let { favoriteMovieHelper?.insertProvider(it) }
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, FavoriteMovieFragment.DataObserver(Handler(), context))
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        favoriteMovieHelper?.open()
        val updated = when(sUriMatcher.match(uri)) {
            FAVORITE_MOVIE_ID -> favoriteMovieHelper?.updateProvider(uri.lastPathSegment, values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, FavoriteMovieFragment.DataObserver(Handler(), context))
        return updated!!
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        favoriteMovieHelper?.open()
        val deleted = when(sUriMatcher.match(uri)) {
            FAVORITE_MOVIE_ID -> favoriteMovieHelper?.deleteProvider(uri.lastPathSegment)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, FavoriteMovieFragment.DataObserver(Handler(), context))
        return deleted!!
    }
}