package andi.android.madegdk.database

import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.TABLE_FAVORITE_TV_SERIES
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.TV_SERIES_ID
import andi.android.madegdk.model.Favorite
import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID

class FavoriteTvSeriesHelper(context: Context?) {

    val DATABASE_TABLE_TV_SERIES = TABLE_FAVORITE_TV_SERIES
    private lateinit var database: SQLiteDatabase
    private var databaseHelper = DatabaseHelper(context)

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

    fun getAllFavoriteTvSeries(): ArrayList<Favorite> {

        val arrayList = ArrayList<Favorite>()
        val cursor = database.query(DATABASE_TABLE_TV_SERIES, null,
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
                        cursor.getString(cursor.getColumnIndexOrThrow(TV_SERIES_ID))
                )
                arrayList.add(favorite)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }

        cursor.close()
        return arrayList
    }

    fun insertNote(favoriteId: String): Long {
        val args = ContentValues()
        args.put(TV_SERIES_ID, favoriteId)
        return database.insert(DATABASE_TABLE_TV_SERIES, null, args)
    }

    fun deleteNote(id: Int): Int {
        return database.delete(TABLE_FAVORITE_TV_SERIES, "$_ID = '$id'", null)
    }
}