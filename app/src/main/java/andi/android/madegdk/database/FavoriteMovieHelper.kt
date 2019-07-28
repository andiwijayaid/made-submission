package andi.android.madegdk.database

import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.MOVIE_ID
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.TABLE_FAVORITE_MOVIE
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID

class FavoriteMovieHelper(context: Context?) {

    private var databaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun isFavorite(movieId: Int?): Boolean {
        val query = "SELECT * FROM $TABLE_FAVORITE_MOVIE WHERE $MOVIE_ID = $movieId"
        val cursor = database.rawQuery(query, null)
        if (cursor.count > 0) {
            cursor.close()
            return true
        }
        cursor.close()
        return false
    }

    fun queryByIdProvider(id: String): Cursor {
        return database.query(
                TABLE_FAVORITE_MOVIE,
                null,
                "$_ID = ?",
                arrayOf(id),
                null,
                null,
                null,
                null
        )
    }

    fun queryProvider(): Cursor {
        return database.query(
                TABLE_FAVORITE_MOVIE,
                null,
                null,
                null,
                null,
                null,
                "$_ID ASC"
        )
    }

    fun insertProvider(values: ContentValues): Long {
        return database.insert(TABLE_FAVORITE_MOVIE, null, values)
    }

    fun updateProvider(movieId: String, values: ContentValues?): Int {
        return database.update(TABLE_FAVORITE_MOVIE, values, "$_ID = ?", arrayOf(movieId))
    }

    fun deleteProvider(movieId: String): Int {
        return database.delete(TABLE_FAVORITE_MOVIE, "$MOVIE_ID = '$movieId'", null)
    }

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
}