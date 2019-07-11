package andi.android.madegdk.ui.home.movie

import andi.android.madegdk.model.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel : ViewModel(), MovieContract.View {
    override fun onFail() {
        this.movies.postValue(null)
    }

    private val movies = MutableLiveData<ArrayList<Movie>>()

    override fun onMoviesRetrieved(movies: ArrayList<Movie>?) {
        val listItems = ArrayList<Movie>()

        if (movies != null) {
            for (i in 0 until movies.size) {
                listItems.add(movies[i])
            }
            this.movies.postValue(movies)
        }
    }

    private lateinit var presenter: MoviePresenter

    fun setMovies(languageCode: String, page: Int) {
        presenter = MoviePresenter(this)
        presenter.getMovies(languageCode, page)
    }

    fun getMovies(): LiveData<ArrayList<Movie>> {
        return movies
    }

}