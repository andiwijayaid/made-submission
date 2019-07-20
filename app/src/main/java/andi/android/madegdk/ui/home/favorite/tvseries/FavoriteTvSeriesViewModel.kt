package andi.android.madegdk.ui.home.favorite.tvseries

import andi.android.madegdk.database.FavoriteTvSeriesHelper
import andi.android.madegdk.model.TvSeries
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavoriteTvSeriesViewModel : ViewModel() {

    private val favoriteTvSeries = MutableLiveData<ArrayList<TvSeries>>()

    fun setTvSeries(context: Context?) {
        val favoriteTvSeriesHelper = FavoriteTvSeriesHelper.getInstance(context)
        favoriteTvSeries.postValue(favoriteTvSeriesHelper?.getAllFavoriteTvSeries())
    }

    fun getTvSeries(): LiveData<ArrayList<TvSeries>> {
        return favoriteTvSeries
    }

}