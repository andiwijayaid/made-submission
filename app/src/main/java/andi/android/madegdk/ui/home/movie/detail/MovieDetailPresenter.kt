package andi.android.madegdk.ui.home.movie.detail

import andi.android.madegdk.BuildConfig
import andi.android.madegdk.api.ApiConfig
import andi.android.madegdk.response.MovieDetailResponse
import android.util.Log
import retrofit2.Call
import retrofit2.Response

class MovieDetailPresenter(val view: MovieDetailContract.View) : MovieDetailContract.Presenter {

    override fun getMovieDetail(id: Int?, languageCode: String) {
        ApiConfig().instance().getMovieDetail(id, BuildConfig.TOKEN, languageCode)
                .enqueue(object : retrofit2.Callback<MovieDetailResponse> {
                    override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                        Log.d("M Fail", t.message)
                    }

                    override fun onResponse(call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>) {
                        val movie = MovieDetailResponse(
                                response.body()?.budget,
                                response.body()?.revenue,
                                response.body()?.runtime
                        )
                        view.onMovieDetailRetrieved(movie)
                        Log.d("RES", movie.toString())
                    }

                })
    }

}