package andi.android.madegdk.database

import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.BACKDROP
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.DATE
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.MOVIE_ID
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.OVERVIEW
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.POSTER
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.RATING
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.TABLE_FAVORITE_MOVIE
import andi.android.madegdk.database.DatabaseContract.FavoriteMoviesColumn.Companion.TITLE
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.FIRST_AIR_DATE
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

        private val SQL_CREATE_TABLE_FAVORITE_MOVIE = String.format("CREATE TABLE $TABLE_FAVORITE_MOVIE"
                + " ($_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $MOVIE_ID TEXT NOT NULL," +
                " $POSTER TEXT NOT NULL," +
                " $BACKDROP TEXT NOT NULL," +
                " $TITLE TEXT NOT NULL," +
                " $DATE TEXT NOT NULL," +
                " $RATING REAL NOT NULL," +
                " $OVERVIEW TEXT NOT NULL)"
        )

        private val SQL_CREATE_TABLE_FAVORITE_TV_SERIES = String.format("CREATE TABLE $TABLE_FAVORITE_TV_SERIES"
                + " ($_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $TV_SERIES_ID TEXT NOT NULL," +
                " $POSTER TEXT NOT NULL," +
                " $BACKDROP TEXT NOT NULL," +
                " $TITLE TEXT NOT NULL," +
                " $FIRST_AIR_DATE TEXT NOT NULL," +
                " $RATING TEXT NOT NULL," +
                " $OVERVIEW TEXT NOT NULL)"
        )
    }

}