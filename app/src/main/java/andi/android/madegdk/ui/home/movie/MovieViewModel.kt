package andi.android.madegdk.ui.home.movie

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

class MovieViewModel : ViewModel() {

    private val movies = MutableLiveData<ArrayList<Movie>>()

    fun setMovies(languageCode: String, page: Int) {
        ApiConfig().instance().getMovies(
                BuildConfig.TOKEN, languageCode, page
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