package andi.android.madegdk.database

import android.provider.BaseColumns

class DatabaseContract {
    class FavoriteMoviesColumn : BaseColumns {
        companion object {
            val TABLE_FAVORITE_MOVIE = "favorite_movie"
            val MOVIE_ID = "movie_id"
        }
    }
    class FavoriteTvSeriesColumn : BaseColumns {
        companion object {
            val TABLE_FAVORITE_TV_SERIES = "favorite_tv_series"
            val TV_SERIES_ID = "tv_series_id"
        }
    }
}