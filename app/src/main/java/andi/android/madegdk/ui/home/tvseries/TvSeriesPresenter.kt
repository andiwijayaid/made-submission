package andi.android.madegdk.ui.home.tvseries

import andi.android.madegdk.BuildConfig
import andi.android.madegdk.api.ApiConfig
import andi.android.madegdk.response.TvSeriesResponse
import android.util.Log
import retrofit2.Call
import retrofit2.Response

class TvSeriesPresenter(val view: TvSeriesContract.View) : TvSeriesContract.Presenter {

    override fun getTvSeries(languageCode: String, page: Int) {
        ApiConfig().instance().getTvSeries(
                BuildConfig.TOKEN, languageCode, page
        ).enqueue(object : retrofit2.Callback<TvSeriesResponse> {
            override fun onFailure(call: Call<TvSeriesResponse>, t: Throwable) {
                Log.d("M Fail", t.message)
            }

            override fun onResponse(call: Call<TvSeriesResponse>, response: Response<TvSeriesResponse>) {
                view.onTvSeriesRetrieved(response.body()?.tvSeries)
                Log.d("RES", response.body().toString())
            }

        })
    }

}