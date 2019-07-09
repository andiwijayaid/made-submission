package andi.android.madegdk.ui.home.tvseries

import andi.android.madegdk.model.TvSeries

interface TvSeriesContract {

    interface Presenter {
        fun getTvSeries(languageCode: String, page: Int)
    }

    interface View {
        fun onTvSeriesRetrieved(tvSeries: ArrayList<TvSeries>?)
    }

}