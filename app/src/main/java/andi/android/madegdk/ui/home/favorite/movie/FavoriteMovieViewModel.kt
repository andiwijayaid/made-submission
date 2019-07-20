package andi.android.madegdk.ui.home.favorite.movie

import andi.android.madegdk.database.FavoriteMovieHelper
import andi.android.madegdk.model.Movie
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavoriteMovieViewModel : ViewModel() {

    private val favoriteMovies = MutableLiveData<ArrayList<Movie>>()

    fun setMovies(context: Context?) {
        val favoriteMovieHelper = FavoriteMovieHelper.getInstance(context)
        favoriteMovies.postValue(favoriteMovieHelper?.getAllFavoriteMovies())
    }

    fun getMovies(): LiveData<ArrayList<Movie>> {
        return favoriteMovies
    }

}