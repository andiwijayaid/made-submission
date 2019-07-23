package andi.android.madegdk.ui.home.favorite.movie

import andi.android.madegdk.model.Movie

interface LoadFavoriteMoviesCallback {
    fun preExecute()
    fun postExecute(favoriteMovies: ArrayList<Movie>?)
}