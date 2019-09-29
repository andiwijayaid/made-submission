package andi.android.popfav.database

import android.net.Uri
import android.provider.BaseColumns

class DatabaseContract {

    companion object {
        const val AUTHORITY = "andi.android.madegdk"
        private const val SCHEME = "content"
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
}