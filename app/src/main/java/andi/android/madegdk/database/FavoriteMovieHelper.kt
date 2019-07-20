package andi.android.madegdk.database

import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.BACKDROP
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.DATE
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.MOVIE_ID
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.OVERVIEW
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.POSTER
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.RATING
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.TABLE_FAVORITE_MOVIE
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.TITLE
import andi.android.madegdk.model.Movie
import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID

class FavoriteMovieHelper(context: Context?) {

    val DATABASE_TABLE_MOVIE = TABLE_FAVORITE_MOVIE
    private lateinit var database: SQLiteDatabase
    private var databaseHelper = DatabaseHelper(context)

    companion object {

        private var INSTANCE: FavoriteMovieHelper? = null
        fun getInstance(context: Context?): FavoriteMovieHelper? {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = FavoriteMovieHelper(context)
                    }
                }
            }
            return INSTANCE
        }

    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.getWritableDatabase()
    }

    fun close() {
        databaseHelper.close()
        if (database.isOpen) {
            database.close()
        }
    }

    fun getAllFavoriteMovies(): ArrayList<Movie> {

        val arrayList = ArrayList<Movie>()
        val cursor = database.query(DATABASE_TABLE_MOVIE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null)
        cursor.moveToFirst()
        if (cursor.count > 0) {
            do {
                val movie = Movie(
                        cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(POSTER)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DATE)),
                        cursor.getFloat(cursor.getColumnIndexOrThrow(RATING)),
                        cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                )
                arrayList.add(movie)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }

        cursor.close()
        return arrayList
    }

    fun insertMovieFavorite(movie: Movie): Long {
        val args = ContentValues()
        args.put(MOVIE_ID, movie.movieId)
        args.put(POSTER, movie.poster)
        args.put(BACKDROP, movie.backdrop)
        args.put(TITLE, movie.title)
        args.put(DATE, movie.date)
        args.put(RATING, movie.date)
        args.put(OVERVIEW, movie.overview)
        return database.insert(DATABASE_TABLE_MOVIE, null, args)
    }

    fun deleteMovieFavorite(id: Int): Int {
        return database.delete(TABLE_FAVORITE_MOVIE, "$_ID = '$id'", null)
    }
}