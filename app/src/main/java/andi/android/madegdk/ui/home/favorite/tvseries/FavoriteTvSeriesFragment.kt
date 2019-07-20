package andi.android.madegdk.ui.home.favorite.tvseries

import andi.android.madegdk.R
import andi.android.madegdk.model.TvSeries
import andi.android.madegdk.ui.home.favorite.tvseries.adapter.FavoriteTvSeriesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_favorite_tv_series.view.*

class FavoriteTvSeriesFragment : Fragment() {

    private lateinit var favoriteTvSeriesView: View

    private lateinit var favoriteTvSeriesAdapter: FavoriteTvSeriesAdapter
    private lateinit var favoriteTvSeriesViewModel: FavoriteTvSeriesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        favoriteTvSeriesView = inflater.inflate(R.layout.fragment_favorite_tv_series, container, false)

        favoriteTvSeriesAdapter = FavoriteTvSeriesAdapter(context) {

        }

        favoriteTvSeriesAdapter.notifyDataSetChanged()
        favoriteTvSeriesView.favoriteTvSeriesRV.adapter = favoriteTvSeriesAdapter
        favoriteTvSeriesView.favoriteTvSeriesRV.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        favoriteTvSeriesViewModel = ViewModelProviders.of(this).get(FavoriteTvSeriesViewModel::class.java)
        favoriteTvSeriesViewModel.setTvSeries(context)
        favoriteTvSeriesViewModel.getTvSeries().observe(this, getFavoriteTvSeries)

        return favoriteTvSeriesView

    }

    private val getFavoriteTvSeries = Observer<ArrayList<TvSeries>> {
        favoriteTvSeriesAdapter.setMovies(it)
    }

}