package andi.android.madegdk.ui.home.movie

import andi.android.madegdk.model.Movie

interface MovieContract {

    interface Presenter {
        fun getMovies(languageCode: String, page: Int)
    }

    interface View {
        fun onMoviesRetrieved(movies: ArrayList<Movie>?)
        fun onFail()
    }

}