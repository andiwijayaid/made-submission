package andi.android.madegdk.ui.home.favorite

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FavoriteViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private val fragmentList = mutableListOf<Fragment>()
    private val fragmentTitleList = mutableListOf<String>()

    override fun getItem(p0: Int): Fragment {
        return fragmentList[p0]
    }

    override fun getCount(): Int {
        return fragmentTitleList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

}