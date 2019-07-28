package andi.android.madegdk.database

import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class DatabaseContract {

    companion object {
        const val AUTHORITY = "andi.android.madegdk"
        private const val SCHEME = "content"

        fun getColumnString(cursor: Cursor, columnName: String): String? {
            return cursor.getString(cursor.getColumnIndex(columnName))
        }

        fun getColumnInt(cursor: Cursor, columnName: String): Int {
            return cursor.getInt(cursor.getColumnIndex(columnName))
        }

        fun getColumnFloat(cursor: Cursor, columnName: String): Float {
            return cursor.getFloat(cursor.getColumnIndex(columnName))
        }
    }

    class FavoriteMoviesColumn : BaseColumns {
        companion object {
            const val TABLE_FAVORITE_MOVIE = "favorite_movie"
            const val MOVIE_ID = "movie_id"
            const val POSTER = "poster"
            const val BACKDROP = "backdrop"
            const val TITLE = "title"
            const val DATE = "date"
            const val RATING = "rating"
            const val OVERVIEW = "overview"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(TABLE_FAVORITE_MOVIE)
                    .build()
        }
    }

    class FavoriteTvSeriesColumn : BaseColumns {
        companion object {
            const val TABLE_FAVORITE_TV_SERIES = "favorite_tv_series"
            const val TV_SERIES_ID = "tv_series_id"
            const val POSTER = "poster"
            const val BACKDROP = "backdrop"
            const val TITLE = "title"
            const val FIRST_AIR_DATE = "first_air_date"
            const val RATING = "rating"
            const val OVERVIEW = "overview"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(TABLE_FAVORITE_TV_SERIES)
                    .build()
        }
    }
}