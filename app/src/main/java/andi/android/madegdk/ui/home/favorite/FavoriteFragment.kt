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
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_favorite.view.*

class FavoriteFragment : Fragment() {

    private lateinit var favoriteView: View
    private lateinit var favoriteMovieFragment: FavoriteMovieFragment
    private lateinit var favoriteTvSeriesFragment: FavoriteTvSeriesFragment
    private lateinit var activeFragment: Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        favoriteView = inflater.inflate(R.layout.fragment_favorite, container, false)

        favoriteMovieFragment = FavoriteMovieFragment()
        favoriteTvSeriesFragment = FavoriteTvSeriesFragment()
        activeFragment = favoriteMovieFragment
        selectFragment(activeFragment)
        setupTabLayout()
        bindWidgetWithEvent()

        return favoriteView
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            Log.d("VIS", "FavoriteFragment")
            selectFragment(activeFragment)
        }
    }

    private fun bindWidgetWithEvent() {
        favoriteView.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}

            override fun onTabUnselected(p0: TabLayout.Tab?) {}

            override fun onTabSelected(p0: TabLayout.Tab?) {
                setCurrentTabFragment(p0?.position)
            }

        })
    }

    private fun setCurrentTabFragment(position: Int?) {
        when (position) {
            0 -> selectFragment(favoriteMovieFragment)
            1 -> selectFragment(favoriteTvSeriesFragment)
        }
    }

    private fun selectFragment(fragment: Fragment) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
        activeFragment = fragment
    }

    private fun setupTabLayout() {
        favoriteView.tabLayout.addTab(favoriteView.tabLayout.newTab().setText(getString(R.string.movie)), true)
        favoriteView.tabLayout.addTab(favoriteView.tabLayout.newTab().setText(getString(R.string.series)))
    }
}