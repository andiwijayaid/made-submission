package andi.android.madegdk.ui.home.favorite.tvseries

import android.database.Cursor

interface LoadFavoriteTvSeriesCallback {
    fun preExecute()
    fun postExecute(favoriteTvSeries: Cursor?)
}