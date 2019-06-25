package andi.android.madegdk.ui.home.tvseries

import andi.android.madegdk.R
import andi.android.madegdk.model.TvSeries
import andi.android.madegdk.model.TvSeriesCollection
import andi.android.madegdk.ui.home.tvseries.adapter.TvSeriesAdapter
import andi.android.madegdk.utils.isIndonesian
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_tv_series.view.*
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

class TvSeriesFragment : Fragment() {

    private lateinit var tvSeriesAdapter: TvSeriesAdapter
    private lateinit var tvSeriesCollection: TvSeriesCollection
    private var tvSeriesData: ArrayList<TvSeries> = arrayListOf()

    private val extraTvSeries = "EXTRA_TV_SERIES"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_tv_series, container, false)

        readJson()
        initTvSeries()


        tvSeriesAdapter = TvSeriesAdapter(context, tvSeriesData) {
            val intent = Intent(context, TvSeriesDetailActivity::class.java)
            intent.putExtra(extraTvSeries, it)
            startActivity(intent)
        }
        view.tvSeriesRV.adapter = tvSeriesAdapter
        view.tvSeriesRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return view
    }

    private fun initTvSeries() {
        tvSeriesData.clear()
        for (i in 0 until tvSeriesCollection.tvSeries!!.size) {
            val tvSeries = TvSeries(
                    tvSeriesCollection.tvSeries!![i].poster,
                    tvSeriesCollection.tvSeries!![i].title,
                    tvSeriesCollection.tvSeries!![i].date,
                    tvSeriesCollection.tvSeries!![i].rating,
                    tvSeriesCollection.tvSeries!![i].runtime,
                    tvSeriesCollection.tvSeries!![i].numberOfEpisode,
                    tvSeriesCollection.tvSeries!![i].language,
                    tvSeriesCollection.tvSeries!![i].overview
            )
            tvSeriesData.add(tvSeries)
        }
    }

    private fun readJson() {
        val jsonString: String

        try {
            val inputStream: InputStream? = if (isIndonesian()) {
                context?.assets?.open("tv_series_indonesian.json")
            } else {
                context?.assets?.open("tv_series.json")
            }
            val size = inputStream?.available()
            val buffer = ByteArray(size!!)
            inputStream.read(buffer)
            inputStream.close()

            jsonString = String(buffer, StandardCharsets.UTF_8)

            val gson = Gson()
            tvSeriesCollection = gson.fromJson(jsonString, TvSeriesCollection::class.java)

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}