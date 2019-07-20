package andi.android.madegdk.database

import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.MOVIE_ID
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.TABLE_FAVORITE_MOVIE
import andi.android.madegdk.model.Favorite
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

    fun getAllFavoriteMovies(): ArrayList<Favorite> {

        val arrayList = ArrayList<Favorite>()
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
                val favorite = Favorite(
                        cursor.getInt(cursor.getColumnIndexOrThrow(_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_ID))
                )
                arrayList.add(favorite)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }

        cursor.close()
        return arrayList
    }

    fun insertMovieFavorite(favoriteId: String): Long {
        val args = ContentValues()
        args.put(MOVIE_ID, favoriteId)
        return database.insert(DATABASE_TABLE_MOVIE, null, args)
    }

    fun deleteMovieFavorite(id: Int): Int {
        return database.delete(TABLE_FAVORITE_MOVIE, "$_ID = '$id'", null)
    }
}