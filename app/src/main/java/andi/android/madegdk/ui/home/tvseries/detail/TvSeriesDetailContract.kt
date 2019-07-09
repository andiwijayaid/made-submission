package andi.android.madegdk.ui.home.tvseries.detail

import andi.android.madegdk.response.TvSeriesDetailResponse

interface TvSeriesDetailContract {

    interface Presenter {
        fun getTvSeriesDetail(id: Int?, languageCode: String)
    }

    interface View {
        fun onTvSeriesDetailRetrieved(tvSeriesDetailResponse: TvSeriesDetailResponse)
    }

}