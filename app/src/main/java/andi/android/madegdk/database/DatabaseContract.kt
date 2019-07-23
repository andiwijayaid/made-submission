package andi.android.madegdk.database

import android.provider.BaseColumns

class DatabaseContract {
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
        }
    }
}