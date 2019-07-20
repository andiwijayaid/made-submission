package andi.android.madegdk.database

import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.BACKDROP
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.FIRST_AIR_DATE
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.OVERVIEW
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.POSTER
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.RATING
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.TABLE_FAVORITE_TV_SERIES
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.TITLE
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.TV_SERIES_ID
import andi.android.madegdk.model.TvSeries
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

    fun getAllFavoriteTvSeries(): ArrayList<TvSeries> {

        val arrayList = ArrayList<TvSeries>()
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
                val tvSeries = TvSeries(
                        cursor.getInt(cursor.getColumnIndexOrThrow(TV_SERIES_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(POSTER)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP)),
                        cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(FIRST_AIR_DATE)),
                        cursor.getFloat(cursor.getColumnIndexOrThrow(RATING)),
                        cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                )
                arrayList.add(tvSeries)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }

        cursor.close()
        return arrayList
    }

    fun insertTvSeries(tvSeries: TvSeries): Long {
        val args = ContentValues()
        args.put(TV_SERIES_ID, tvSeries.tvSeriesId)
        args.put(POSTER, tvSeries.poster)
        args.put(BACKDROP, tvSeries.backdrop)
        args.put(TITLE, tvSeries.title)
        args.put(FIRST_AIR_DATE, tvSeries.firstAirDate)
        args.put(RATING, tvSeries.rating)
        args.put(OVERVIEW, tvSeries.overview)
        return database.insert(DATABASE_TABLE_TV_SERIES, null, args)
    }

    fun deleteNote(id: Int): Int {
        return database.delete(TABLE_FAVORITE_TV_SERIES, "$_ID = '$id'", null)
    }
}