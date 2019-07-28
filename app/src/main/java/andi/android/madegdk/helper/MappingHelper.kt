package andi.android.madegdk.helper

import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.BACKDROP
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.DATE
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.MOVIE_ID
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.OVERVIEW
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.POSTER
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.RATING
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.TITLE
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.FIRST_AIR_DATE
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.TV_SERIES_ID
import andi.android.madegdk.model.Movie
import andi.android.madegdk.model.TvSeries
import android.database.Cursor


fun mapFavoriteMovieCursorToArrayList(cursor: Cursor?): ArrayList<Movie> {
    val favoriteMovieList = arrayListOf<Movie>()
    if (cursor != null) {
        while (cursor.moveToNext()) {
            val movieId = cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID))
            val poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER))
            val backdrop = cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(DATE))
            val rating = cursor.getFloat(cursor.getColumnIndexOrThrow(RATING))
            val overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW))
            favoriteMovieList.add(Movie(
                    movieId, poster, backdrop, title, date, rating, overview
            ))
        }
    }
    return favoriteMovieList
}

fun mapFavoriteTvSeriesCursorToArrayList(cursor: Cursor?): ArrayList<TvSeries> {
    val favoriteTvSeriesList = arrayListOf<TvSeries>()
    if (cursor != null) {
        while (cursor.moveToNext()) {
            val tvSeriesId = cursor.getInt(cursor.getColumnIndexOrThrow(TV_SERIES_ID))
            val poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER))
            val backdrop = cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE))
            val firstAirDate = cursor.getString(cursor.getColumnIndexOrThrow(FIRST_AIR_DATE))
            val rating = cursor.getFloat(cursor.getColumnIndexOrThrow(RATING))
            val overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW))
            favoriteTvSeriesList.add(TvSeries(
                    tvSeriesId, poster, backdrop, title, firstAirDate, rating, overview
            ))
        }
    }
    return favoriteTvSeriesList
}