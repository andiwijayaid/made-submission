package andi.android.popfav.helper

import andi.android.popfav.database.DatabaseContract.FavoriteMoviesColumn.Companion.BACKDROP
import andi.android.popfav.database.DatabaseContract.FavoriteMoviesColumn.Companion.DATE
import andi.android.popfav.database.DatabaseContract.FavoriteMoviesColumn.Companion.MOVIE_ID
import andi.android.popfav.database.DatabaseContract.FavoriteMoviesColumn.Companion.OVERVIEW
import andi.android.popfav.database.DatabaseContract.FavoriteMoviesColumn.Companion.POSTER
import andi.android.popfav.database.DatabaseContract.FavoriteMoviesColumn.Companion.RATING
import andi.android.popfav.database.DatabaseContract.FavoriteMoviesColumn.Companion.TITLE
import andi.android.popfav.model.Movie
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