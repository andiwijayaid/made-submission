package andi.android.madegdk.ui.home.tvseries

import andi.android.madegdk.BuildConfig
import andi.android.madegdk.api.ApiConfig
import andi.android.madegdk.model.TvSeries
import andi.android.madegdk.response.TvSeriesResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response

class TvSeriesViewModel : ViewModel() {

    private val tvSeries = MutableLiveData<ArrayList<TvSeries>>()

    fun setTvSeries(languageCode: String, page: Int) {
        ApiConfig().instance().getTvSeries(
                BuildConfig.TOKEN, languageCode, page
        ).enqueue(object : retrofit2.Callback<TvSeriesResponse> {
            override fun onFailure(call: Call<TvSeriesResponse>, t: Throwable) {
                Log.d("M Fail", t.message)
                tvSeries.postValue(null)
            }

            override fun onResponse(call: Call<TvSeriesResponse>, response: Response<TvSeriesResponse>) {
                val listItems = response.body()?.tvSeries
                tvSeries.postValue(listItems)
            }

        })
    }

    fun getTvSeries(): LiveData<ArrayList<TvSeries>> {
        return tvSeries
    }

    fun countRetrievedTvSeries(): Int? {
        return this.tvSeries.value?.size
    }
}