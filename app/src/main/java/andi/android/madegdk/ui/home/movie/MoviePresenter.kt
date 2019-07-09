package andi.android.madegdk.ui.home.movie

import andi.android.madegdk.BuildConfig
import andi.android.madegdk.api.ApiConfig
import andi.android.madegdk.response.MovieResponse
import android.util.Log
import retrofit2.Call
import retrofit2.Response

class MoviePresenter(val view: MovieContract.View) : MovieContract.Presenter {

    override fun getMovies(languageCode: String, page: Int) {
        ApiConfig().instance().getMovies(
                BuildConfig.TOKEN, languageCode, page
        ).enqueue(object : retrofit2.Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("M Fail", t.message)
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                view.onMoviesRetrieved(response.body()?.movies)
                Log.d("RES", response.body().toString())
            }

        })
    }

}