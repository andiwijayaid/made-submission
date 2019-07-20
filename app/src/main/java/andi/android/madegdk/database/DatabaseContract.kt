package andi.android.madegdk.database

import android.provider.BaseColumns

class DatabaseContract {
    class FavoriteMoviesColumn : BaseColumns {
        companion object {
            val TABLE_FAVORITE_MOVIE = "favorite_movie"
            val MOVIE_ID = "movie_id"
            val POSTER = "poster"
            val BACKDROP = "backdrop"
            val TITLE = "title"
            val DATE = "date"
            val RATING = "rating"
            val OVERVIEW = "overview"
        }
    }
    class FavoriteTvSeriesColumn : BaseColumns {
        companion object {
            val TABLE_FAVORITE_TV_SERIES = "favorite_tv_series"
            val TV_SERIES_ID = "tv_series_id"
            val POSTER = "poster"
            val BACKDROP = "backdrop"
            val TITLE = "title"
            val FIRST_AIR_DATE = "first_air_date"
            val RATING = "rating"
            val OVERVIEW = "overview"
        }
    }
}