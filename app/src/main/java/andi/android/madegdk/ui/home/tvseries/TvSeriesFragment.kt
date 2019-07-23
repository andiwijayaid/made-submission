package andi.android.madegdk.ui.home.tvseries

import andi.android.madegdk.R
import andi.android.madegdk.model.TvSeries
import andi.android.madegdk.ui.home.tvseries.adapter.TvSeriesAdapter
import andi.android.madegdk.ui.home.tvseries.detail.TvSeriesDetailActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_tv_series.*
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

        tvSeriesAdapter = TvSeriesAdapter(context) {
            val intent = Intent(context, TvSeriesDetailActivity::class.java)
            intent.putExtra(extraTvSeries, it)
            startActivity(intent)
        }
        tvSeriesAdapter.notifyDataSetChanged()
        tvSeriesView.tvSeriesRV.adapter = tvSeriesAdapter
        tvSeriesView.tvSeriesRV.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        tvSeriesViewModel = ViewModelProviders.of(this).get(TvSeriesViewModel::class.java)
        if (tvSeriesViewModel.countRetrievedTvSeries() == null) {
            tvSeriesViewModel.setTvSeries(resources.getString(R.string.language_code), page)
            showLoading(true)
        }
        tvSeriesViewModel.getTvSeries().observe(this, getTvSeries)

        tvSeriesView.refreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
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

    private val getTvSeries = Observer<ArrayList<TvSeries>> { tvSeries ->
        showLoading(false)
        tvSeriesView.refreshLayout.finishRefresh(true)
        tvSeriesView.refreshLayout.finishLoadMore(true)
        if (tvSeries != null) {
            if (page == 1) {
                tvSeriesAdapter.setTvSeries(tvSeries)
            } else {
                tvSeriesAdapter.addTvSeries(tvSeries)
            }
            onFailLL.visibility = View.GONE
        } else {
            onFailLL.visibility = View.VISIBLE
            Toast.makeText(context, resources.getString(R.string.check_your_connection), Toast.LENGTH_LONG).show()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            tvSeriesView.progressBar.visibility = View.VISIBLE
        } else {
            tvSeriesView.progressBar.visibility = View.GONE
        }
    }
}