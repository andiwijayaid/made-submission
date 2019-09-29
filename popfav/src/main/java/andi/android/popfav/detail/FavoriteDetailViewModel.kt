package andi.android.popfav.detail

import andi.android.popfav.BuildConfig
import andi.android.popfav.api.ApiConfig
import andi.android.popfav.model.MovieDetailResponse
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response

class FavoriteDetailViewModel: ViewModel() {

    private var movieDetailResponse = MutableLiveData<MovieDetailResponse>()

    fun setMovie(id: Int?, languageCode: String) {
        ApiConfig().instance().getMovieDetail(id, BuildConfig.TOKEN, languageCode)
                .enqueue(object : retrofit2.Callback<MovieDetailResponse> {
                    override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                        Log.d("M Fail", t.message)
                        movieDetailResponse.postValue(null)
                    }

                    override fun onResponse(call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>) {
                        val movie = MovieDetailResponse(
                                response.body()?.budget,
                                response.body()?.revenue,
                                response.body()?.runtime
                        )
                        movieDetailResponse.postValue(movie)
                    }

                })
    }

    fun getMovie(): MutableLiveData<MovieDetailResponse>? {
        return movieDetailResponse
    }

    fun isMovieRetrieved(): Boolean {
        return movieDetailResponse.value != null
    }
}