package andi.android.madegdk.ui.home.tvseries

import andi.android.madegdk.model.TvSeries
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TvSeriesViewModel : ViewModel(), TvSeriesContract.View {
    override fun onFail() {
        this.tvSeries.postValue(null)
    }

    private val tvSeries = MutableLiveData<ArrayList<TvSeries>>()

    override fun onTvSeriesRetrieved(tvSeries: ArrayList<TvSeries>?) {
        val listItems = ArrayList<TvSeries>()

        if (tvSeries != null) {
            for (i in 0 until tvSeries.size) {
                listItems.add(tvSeries[i])
            }
            this.tvSeries.postValue(tvSeries)
        }
    }

    private lateinit var presenter: TvSeriesPresenter

    fun setTvSeries(languageCode: String, page: Int) {
        presenter = TvSeriesPresenter(this)
        presenter.getTvSeries(languageCode, page)
    }

    fun getTvSeries(): LiveData<ArrayList<TvSeries>> {
        return tvSeries
    }
}