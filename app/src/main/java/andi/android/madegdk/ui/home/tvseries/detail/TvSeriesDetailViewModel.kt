package andi.android.madegdk.ui.home.tvseries.detail

import andi.android.madegdk.BuildConfig
import andi.android.madegdk.api.ApiConfig
import andi.android.madegdk.response.TvSeriesDetailResponse
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response

class TvSeriesDetailViewModel : ViewModel() {

    private var tvSeriesDetailResponse = MutableLiveData<TvSeriesDetailResponse>()

    fun setTvSeries(id: Int?, languageCode: String) {
        ApiConfig().instance().getTvSeriesDetail(id, BuildConfig.TOKEN, languageCode)
                .enqueue(object : retrofit2.Callback<TvSeriesDetailResponse> {
                    override fun onFailure(call: Call<TvSeriesDetailResponse>, t: Throwable) {
                        Log.d("M Fail", t.message)
                        tvSeriesDetailResponse.postValue(null)
                    }

                    override fun onResponse(call: Call<TvSeriesDetailResponse>, response: Response<TvSeriesDetailResponse>) {
                        val aTvSeries = TvSeriesDetailResponse(
                                response.body()?.numberOfEpisodes,
                                response.body()?.numberOfSeasons
                        )
                        tvSeriesDetailResponse.postValue(aTvSeries)
                    }
                })
    }

    fun getTvSeries(): MutableLiveData<TvSeriesDetailResponse>? {
        return tvSeriesDetailResponse
    }

    fun isTvSeriesRetrieved(): Boolean {
        return tvSeriesDetailResponse.value != null
    }

}