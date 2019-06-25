package andi.android.madegdk.ui.home

import andi.android.madegdk.R
import andi.android.madegdk.ui.home.movie.MovieFragment
import andi.android.madegdk.ui.home.tvseries.TvSeriesFragment
import andi.android.madegdk.utils.isIndonesian
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*


class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale()
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.elevation = 0f
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBar.outlineProvider = null
        }

        homeViewPagerAdapter = HomeViewPagerAdapter(supportFragmentManager)
        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        if (isIndonesian()) {
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_indonesian)
        } else {
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_english)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.language -> showChangeLanguageDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val homeViewPagerAdapter = HomeViewPagerAdapter(supportFragmentManager)
        homeViewPagerAdapter.addFragment(MovieFragment(), getString(R.string.movie))
        homeViewPagerAdapter.addFragment(TvSeriesFragment(), getString(R.string.series))
        viewPager.adapter = homeViewPagerAdapter
    }

    private fun showChangeLanguageDialog() {
        val checkedItem: Int
        if (isIndonesian()) {
            checkedItem = 1
        } else {
            checkedItem = 0
        }
        val listItems = arrayOf(getString(R.string.english), getString(R.string.indonesian))
        val mBuilder = AlertDialog.Builder(this@HomeActivity)
        mBuilder.setTitle(getString(R.string.choose_language))
        mBuilder.setSingleChoiceItems(listItems, checkedItem) { dialogInterface, i ->
            if (i == 0) {
                setLocale("en")
                recreate()
            } else if (i == 1) {
                setLocale("in")
                recreate()
            }
            dialogInterface.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun setLocale(lang: String?) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.getDisplayMetrics())

        val editor = getSharedPreferences("SETTINGS", Context.MODE_PRIVATE).edit()
        editor.putString("MY_LANG", lang)
        editor.apply()
    }

    fun loadLocale() {
        val sharedPreferences = getSharedPreferences("SETTINGS", Activity.MODE_PRIVATE)
        val lang = sharedPreferences.getString("MY_LANG", Locale.getDefault().language)
        setLocale(lang)
    }
}