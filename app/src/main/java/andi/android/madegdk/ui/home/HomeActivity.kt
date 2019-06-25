package andi.android.madegdk.ui.home

import andi.android.madegdk.R
import andi.android.madegdk.ui.home.movie.MovieFragment
import andi.android.madegdk.ui.home.tvseries.TvSeriesFragment
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.elevation = 0f
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBar.outlineProvider = null
        }

        homeViewPagerAdapter = HomeViewPagerAdapter(supportFragmentManager)
        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)


    }

    private fun setupViewPager(viewPager: ViewPager) {
        val homeViewPagerAdapter = HomeViewPagerAdapter(supportFragmentManager)
        homeViewPagerAdapter.addFragment(MovieFragment(), getString(R.string.movie))
        homeViewPagerAdapter.addFragment(TvSeriesFragment(), getString(R.string.series))
        viewPager.adapter = homeViewPagerAdapter
    }
}