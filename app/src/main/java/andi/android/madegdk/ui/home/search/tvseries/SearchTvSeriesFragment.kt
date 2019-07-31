package andi.android.madegdk.ui.home.search.tvseries

import andi.android.madegdk.R
import andi.android.madegdk.database.DatabaseContract.FavoriteTvSeriesColumn.Companion.CONTENT_URI
import andi.android.madegdk.model.TvSeries
import andi.android.madegdk.ui.home.HomeActivity
import andi.android.madegdk.ui.home.tvseries.TvSeriesFragment.Companion.extraTvSeries
import andi.android.madegdk.ui.home.tvseries.adapter.TvSeriesAdapter
import andi.android.madegdk.ui.home.tvseries.detail.TvSeriesDetailActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search_tv_series.view.*

class SearchTvSeriesFragment : Fragment(), HomeActivity.TvSeriesSearchListener {
    override fun sendQueryToSearchTvSeriesFragment(query: String?) {
        Log.d("QUERY TV", query)
        this.query = query.toString()
        searchTvSeriesViewModel.setTvSeries(resources.getString(R.string.language_code), query)
        showLoading(true)
        searchTvSeriesViewModel.getTvSeries().observe(this, getTvSeries)
    }

    private lateinit var tvSeriesAdapter: TvSeriesAdapter

    private lateinit var searchTvSeriesViewModel: SearchTvSeriesViewModel
    private lateinit var searchTvSeriesView: View

    private lateinit var tvSeries: ArrayList<TvSeries>

    private lateinit var query: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        searchTvSeriesView = inflater.inflate(R.layout.fragment_search_tv_series, container, false)

        (activity as HomeActivity).setTvSeriesQueryListener(this)

        tvSeriesAdapter = TvSeriesAdapter(context) { tvSeries: TvSeries, position: Int ->
            val intent = Intent(context, TvSeriesDetailActivity::class.java)
            val uri = Uri.parse("$CONTENT_URI/${tvSeriesAdapter.getTvSeries()[position].tvSeriesId}")
            intent.data = uri
            intent.putExtra(extraTvSeries, tvSeries)
            startActivity(intent)
        }

        tvSeriesAdapter.notifyDataSetChanged()
        searchTvSeriesView.tvSeriesRV.adapter = tvSeriesAdapter
        searchTvSeriesView.tvSeriesRV.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        searchTvSeriesViewModel = ViewModelProviders.of(this).get(SearchTvSeriesViewModel::class.java)

        searchTvSeriesView.refreshLayout.setOnRefreshListener {
            searchTvSeriesViewModel.setTvSeries(resources.getString(R.string.language_code), query)
        }

        return searchTvSeriesView
    }

    private val getTvSeries = Observer<ArrayList<TvSeries>> { tvSeries ->
        this.tvSeries = tvSeries
        showLoading(false)
        searchTvSeriesView.refreshLayout.finishRefresh(true)
        searchTvSeriesView.refreshLayout.finishLoadMore(true)
        if (tvSeries != null) {
            tvSeriesAdapter.setTvSeries(tvSeries)
            searchTvSeriesView.onFailLL.visibility = View.GONE
        } else {
            searchTvSeriesView.onFailLL.visibility = View.VISIBLE
            Toast.makeText(context, resources.getString(R.string.check_your_connection), Toast.LENGTH_LONG).show()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            searchTvSeriesView.progressBar.visibility = View.VISIBLE
        } else {
            searchTvSeriesView.progressBar.visibility = View.GONE
        }
    }

}