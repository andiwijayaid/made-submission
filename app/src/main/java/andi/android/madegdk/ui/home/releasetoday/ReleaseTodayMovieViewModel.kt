package andi.android.madegdk.ui.home.releasetoday

import andi.android.madegdk.BuildConfig
import andi.android.madegdk.api.ApiConfig
import andi.android.madegdk.model.Movie
import andi.android.madegdk.response.MovieResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response

class ReleaseTodayMovieViewModel : ViewModel() {

    private val movies = MutableLiveData<ArrayList<Movie>>()

    fun setMovies(languageCode: String, date: String) {
        ApiConfig().instance().getTodayReleasedMovie(
                BuildConfig.TOKEN, languageCode, date, date
        ).enqueue(object : retrofit2.Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("M Fail", t.message)
                movies.postValue(null)
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val listItems = response.body()?.movies
                movies.postValue(listItems)
            }

        })
    }

    fun getMovies(): LiveData<ArrayList<Movie>> {
        return movies
    }

    fun countRetrievedMovies(): Int? {
        return this.movies.value?.size
    }

}