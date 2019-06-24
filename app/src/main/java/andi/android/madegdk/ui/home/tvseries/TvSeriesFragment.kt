package andi.android.madegdk.ui.home.tvseries

import andi.android.madegdk.R
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TvSeriesFragment: Fragment() {

    private lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_tv_series, container, false)

        return view
    }

}