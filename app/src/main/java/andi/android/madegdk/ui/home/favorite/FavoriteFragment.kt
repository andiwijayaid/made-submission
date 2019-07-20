package andi.android.madegdk.ui.home.favorite

import andi.android.madegdk.R
import andi.android.madegdk.ui.home.favorite.movie.FavoriteMovieFragment
import andi.android.madegdk.ui.home.favorite.tvseries.FavoriteTvSeriesFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_favorite.view.*

class FavoriteFragment : Fragment() {

    private lateinit var favoriteView: View
    private lateinit var favoriteViewPagerAdapter: FavoriteViewPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        favoriteView = inflater.inflate(R.layout.fragment_favorite, container, false)

        setupViewPager(favoriteView.viewPager)
        favoriteView.tabLayout.setupWithViewPager(favoriteView.viewPager)

        return favoriteView
    }

    private fun setupViewPager(viewPager: ViewPager) {
        favoriteViewPagerAdapter = FavoriteViewPagerAdapter(fragmentManager)
        favoriteViewPagerAdapter.addFragment(FavoriteMovieFragment(), getString(R.string.movie))
        favoriteViewPagerAdapter.addFragment(FavoriteTvSeriesFragment(), getString(R.string.series))
        viewPager.adapter = favoriteViewPagerAdapter
    }

}