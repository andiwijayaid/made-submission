package andi.android.madegdk.provider

import andi.android.madegdk.database.DatabaseContract.Companion.AUTHORITY
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.CONTENT_URI
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.TABLE_FAVORITE_TV_SERIES
import andi.android.madegdk.database.FavoriteTvSeriesHelper
import andi.android.madegdk.ui.home.favorite.tvseries.FavoriteTvSeriesFragment
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.os.Handler

class FavoriteTvSeriesProvider : ContentProvider() {

    companion object {
        private const val FAVORITE_TV_SERIES = 11
        private const val FAVORITE_TV_SERIES_ID = 12
    }

    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private var favoriteTvSeriesHelper: FavoriteTvSeriesHelper? = null

    init {
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_TV_SERIES, FAVORITE_TV_SERIES)
        sUriMatcher.addURI(AUTHORITY, "$TABLE_FAVORITE_TV_SERIES/#", FAVORITE_TV_SERIES_ID)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        favoriteTvSeriesHelper?.open()
        val added: Long?
        when(sUriMatcher.match(uri)) {
            FAVORITE_TV_SERIES ->  added = values?.let { favoriteTvSeriesHelper?.insertProvider(it) }
            else -> added = 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, FavoriteTvSeriesFragment.Companion.DataObserver(Handler(), context))
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun query(uri: Uri?, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        favoriteTvSeriesHelper?.open()
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            FAVORITE_TV_SERIES -> cursor = favoriteTvSeriesHelper?.queryProvider()
            FAVORITE_TV_SERIES_ID -> cursor = uri?.lastPathSegment?.let { favoriteTvSeriesHelper?.queryByIdProvider(it) }
            else -> cursor = null
        }
        return cursor
    }

    override fun onCreate(): Boolean {
        favoriteTvSeriesHelper = FavoriteTvSeriesHelper.getInstance(context)
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        favoriteTvSeriesHelper?.open()
        val updated = when(sUriMatcher.match(uri)) {
            FAVORITE_TV_SERIES_ID -> favoriteTvSeriesHelper!!.updateProvider(uri.lastPathSegment, values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, FavoriteTvSeriesFragment.Companion.DataObserver(Handler(), context))
        return updated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        favoriteTvSeriesHelper?.open()
        val deleted = when(sUriMatcher.match(uri)) {
            FAVORITE_TV_SERIES_ID -> favoriteTvSeriesHelper!!.deleteProvider(uri.lastPathSegment)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, FavoriteTvSeriesFragment.Companion.DataObserver(Handler(), context))
        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}