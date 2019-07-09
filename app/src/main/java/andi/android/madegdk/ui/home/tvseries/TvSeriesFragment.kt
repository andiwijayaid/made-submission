package andi.android.madegdk.ui.home.tvseries

import andi.android.madegdk.R
import andi.android.madegdk.model.TvSeries
import andi.android.madegdk.ui.home.tvseries.adapter.TvSeriesAdapter
import andi.android.madegdk.ui.home.tvseries.detail.TvSeriesDetailActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_tv_series.view.*
import java.util.*

class TvSeriesFragment : Fragment() {

    private lateinit var tvSeriesAdapter: TvSeriesAdapter
    private val extraTvSeries = "EXTRA_TV_SERIES"

    private lateinit var tvSeriesViewModel: TvSeriesViewModel
    private lateinit var tvSeriesView: View
    private var page = 1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        tvSeriesView = inflater.inflate(R.layout.fragment_tv_series, container, false)

        tvSeriesViewModel = ViewModelProviders.of(this).get(TvSeriesViewModel::class.java)
        tvSeriesViewModel.setTvSeries(resources.getString(R.string.language_code), page)
        showLoading(true)
        tvSeriesViewModel.getTvSeries().observe(this, getTvSeries)

        tvSeriesAdapter = TvSeriesAdapter(context) {
            val intent = Intent(context, TvSeriesDetailActivity::class.java)
            intent.putExtra(extraTvSeries, it)
            startActivity(intent)
        }
        tvSeriesAdapter.notifyDataSetChanged()
        tvSeriesView.tvSeriesRV.adapter = tvSeriesAdapter
        tvSeriesView.tvSeriesRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        tvSeriesView.refreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener{
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                page += 1
                tvSeriesViewModel.setTvSeries(resources.getString(R.string.language_code), page)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                page = 1
                tvSeriesViewModel.setTvSeries(resources.getString(R.string.language_code), page)
            }

        })

        return tvSeriesView
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            tvSeriesView.progressBar.visibility = View.VISIBLE
        } else {
            tvSeriesView.progressBar.visibility = View.GONE
        }
    }

    private val getTvSeries = Observer<ArrayList<TvSeries>> { tvSeries ->
        if (tvSeries != null) {
            if (page == 1) {
                tvSeriesAdapter.setTvSeries(tvSeries)
            } else {
                tvSeriesAdapter.addTvSeries(tvSeries)
            }
            showLoading(false)
            tvSeriesView.refreshLayout.finishRefresh(true)
            tvSeriesView.refreshLayout.finishLoadMore(true)
        } else {
            showLoading(true)
        }
    }
}