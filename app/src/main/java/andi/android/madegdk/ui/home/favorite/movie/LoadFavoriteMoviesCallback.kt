package andi.android.madegdk.ui.home.favorite.movie

import android.database.Cursor

interface LoadFavoriteMoviesCallback {
    fun preExecute()
    fun postExecute(favoriteMovies: Cursor)
}