package andi.android.madegdk.database

import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.MOVIE_ID
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.TABLE_FAVORITE_MOVIE
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.TABLE_FAVORITE_TV_SERIES
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.TV_SERIES_ID
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAVORITE_MOVIE)
        db?.execSQL(SQL_CREATE_TABLE_FAVORITE_TV_SERIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${TABLE_FAVORITE_MOVIE}")
        db?.execSQL("DROP TABLE IF EXISTS ${TABLE_FAVORITE_TV_SERIES}")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "dbfavorite"
        const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_FAVORITE_MOVIE = String.format("CREATE TABLE %s"
                + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                " %s TEXT NOT NULL)",
                TABLE_FAVORITE_MOVIE,
                _ID,
                MOVIE_ID
        )

        private val SQL_CREATE_TABLE_FAVORITE_TV_SERIES = String.format("CREATE TABLE %s"
                + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                " %s TEXT NOT NULL)",
                TABLE_FAVORITE_TV_SERIES,
                _ID,
                TV_SERIES_ID
        )
    }

}