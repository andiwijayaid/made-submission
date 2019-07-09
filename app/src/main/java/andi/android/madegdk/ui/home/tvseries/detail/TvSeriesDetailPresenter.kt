package andi.android.madegdk.ui.home.tvseries.detail

import andi.android.madegdk.BuildConfig
import andi.android.madegdk.api.ApiConfig
import andi.android.madegdk.response.TvSeriesDetailResponse
import android.util.Log
import retrofit2.Call
import retrofit2.Response

class TvSeriesDetailPresenter(val view: TvSeriesDetailContract.View) : TvSeriesDetailContract.Presenter {

    override fun getTvSeriesDetail(id: Int?, languageCode: String) {
        ApiConfig().instance().getTvSeriesDetail(id, BuildConfig.TOKEN, languageCode)
                .enqueue(object : retrofit2.Callback<TvSeriesDetailResponse> {
                    override fun onFailure(call: Call<TvSeriesDetailResponse>, t: Throwable) {
                        Log.d("M Fail", t.message)
                    }

                    override fun onResponse(call: Call<TvSeriesDetailResponse>, response: Response<TvSeriesDetailResponse>) {
                        val aTvSeries = TvSeriesDetailResponse(
                                response.body()?.numberOfEpisodes,
                                response.body()?.numberOfSeasons
                        )
                        view.onTvSeriesDetailRetrieved(aTvSeries)
                        Log.d("RES", aTvSeries.toString())
                    }

                })
    }

}