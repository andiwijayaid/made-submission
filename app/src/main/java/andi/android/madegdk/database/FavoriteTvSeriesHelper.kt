package andi.android.madegdk.database

import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.TABLE_FAVORITE_TV_SERIES
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.TV_SERIES_ID
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID

class FavoriteTvSeriesHelper(context: Context?) {

    private var databaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun isFavorite(tvSeriesId: Int?): Boolean {
        val query = "SELECT * FROM $TABLE_FAVORITE_TV_SERIES WHERE $TV_SERIES_ID = $tvSeriesId"
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
                TABLE_FAVORITE_TV_SERIES,
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
                TABLE_FAVORITE_TV_SERIES,
                null,
                null,
                null,
                null,
                null,
                "$_ID ASC"
        )
    }

    fun insertProvider(values: ContentValues): Long {
        return database.insert(TABLE_FAVORITE_TV_SERIES, null, values)
    }

    fun updateProvider(id: String, values: ContentValues?): Int {
        return database.update(TABLE_FAVORITE_TV_SERIES, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteProvider(id: String): Int {
        return database.delete(TABLE_FAVORITE_TV_SERIES, "$TV_SERIES_ID = ?", arrayOf(id))
    }

    companion object {
        private var INSTANCE: FavoriteTvSeriesHelper? = null
        fun getInstance(context: Context?): FavoriteTvSeriesHelper? {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = FavoriteTvSeriesHelper(context)
                    }
                }
            }
            return INSTANCE
        }
    }
}