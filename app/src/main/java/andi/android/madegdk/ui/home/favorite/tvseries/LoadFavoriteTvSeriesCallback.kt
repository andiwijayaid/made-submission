package andi.android.madegdk.ui.home.favorite.tvseries

import andi.android.madegdk.model.TvSeries

interface LoadFavoriteTvSeriesCallback {
    fun preExecute()
    fun postExecute(favoriteTvSeries: ArrayList<TvSeries>?)
}