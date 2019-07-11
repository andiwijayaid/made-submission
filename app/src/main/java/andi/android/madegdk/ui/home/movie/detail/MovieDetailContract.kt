package andi.android.madegdk.ui.home.movie.detail

import andi.android.madegdk.response.MovieDetailResponse

interface MovieDetailContract {

    interface Presenter {
        fun getMovieDetail(id: Int?, languageCode: String)
    }

    interface View {
        fun onMovieDetailRetrieved(movieDetailResponse: MovieDetailResponse)
        fun onFail()
    }
}