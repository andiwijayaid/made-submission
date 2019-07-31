package andi.android.madegdk.ui.home.search

import andi.android.madegdk.R
import andi.android.madegdk.ui.home.movie.MovieFragment
import andi.android.madegdk.ui.home.search.movie.SearchMovieFragment
import andi.android.madegdk.ui.home.search.tvseries.SearchTvSeriesFragment
import andi.android.madegdk.ui.home.tvseries.TvSeriesFragment
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    private lateinit var searchViewPagerAdapter: SearchViewPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.appBar.outlineProvider = null
        }

        setupViewPager(view.viewPager)
        view.tabLayout.setupWithViewPager(view.viewPager)

        return view
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        searchViewPagerAdapter = SearchViewPagerAdapter(childFragmentManager)
        searchViewPagerAdapter.addFragment(SearchMovieFragment(), getString(R.string.movie))
        searchViewPagerAdapter.addFragment(SearchTvSeriesFragment(), getString(R.string.series))
        viewPager?.adapter = searchViewPagerAdapter
    }

}