package andi.android.madegdk.ui.home

import andi.android.madegdk.R
import andi.android.madegdk.reminder.EightInTheMorningReminder
import andi.android.madegdk.reminder.SevenInTheMorningReminder
import andi.android.madegdk.sharedpreferences.FirstOpenPreference
import andi.android.madegdk.ui.home.favorite.FavoriteFragment
import andi.android.madegdk.ui.home.movie.MovieFragment
import andi.android.madegdk.ui.home.search.SearchFragment
import andi.android.madegdk.ui.home.setting.SettingActivity
import andi.android.madegdk.ui.home.tvseries.TvSeriesFragment
import andi.android.madegdk.utils.LanguageManager
import andi.android.madegdk.utils.isIndonesian
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        movieSearchListener?.sendQueryToSearchMovieFragment(query)
        tvSeriesSearchListener?.sendQueryToSearchTvSeriesFragment(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
//        movieSearchListener?.sendQueryToSearchMovieFragment(newText)
        return false
    }

    interface MovieSearchListener {
        fun sendQueryToSearchMovieFragment(query: String?)
    }

    interface TvSeriesSearchListener {
        fun sendQueryToSearchTvSeriesFragment(query: String?)
    }

    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private lateinit var languageManager: LanguageManager
    private var movieSearchListener: MovieSearchListener? = null
    private var tvSeriesSearchListener: TvSeriesSearchListener? = null

    private lateinit var sevenInTheMorningReminder: SevenInTheMorningReminder
    private lateinit var eightInTheMorningReminder: EightInTheMorningReminder

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        languageManager = LanguageManager(this)
        languageManager.loadLocale()
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.elevation = 0f
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBar.outlineProvider = null
        }

        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
        viewPager.offscreenPageLimit = 2

        sevenInTheMorningReminder = SevenInTheMorningReminder()
        eightInTheMorningReminder = EightInTheMorningReminder()

        val firstOpenPreference = FirstOpenPreference(this)
        if (firstOpenPreference.isFirstOpenStatus().toString().toBoolean()) {
            setAlarm()
        }
        firstOpenPreference.setFirstOpenStatus(false)
    }

    private fun setAlarm() {
        sevenInTheMorningReminder.setRepeatingAlarm(applicationContext)
        eightInTheMorningReminder.setRepeatingAlarm(applicationContext)
    }

    fun setMovieQueryListener(movieSearchListener: MovieSearchListener) {
        this.movieSearchListener = movieSearchListener
    }

    fun setTvSeriesQueryListener(tvSeriesSearchListener: TvSeriesSearchListener) {
        this.tvSeriesSearchListener = tvSeriesSearchListener
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        if (isIndonesian(languageManager.getMyLang())) {
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_indonesian)
        } else {
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_english)
        }

        val menuSearch = menu?.findItem(R.id.search)
        val searchView = menuSearch?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.setOnSearchClickListener {
            viewPager.visibility = View.GONE
            tabLayout.visibility = View.GONE
            containerFL.visibility = View.VISIBLE
            showSearch()
        }

        searchView.setOnCloseListener {
            removeSearch()
            viewPager.visibility = View.VISIBLE
            tabLayout.visibility = View.VISIBLE
            containerFL.visibility = View.GONE
            false
        }

        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("PrivateResource")
    private fun reopen() {
        finish()
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.language -> showChangeLanguageDialog()
            R.id.setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private lateinit var searchFragment: SearchFragment

    private fun showSearch() {
        searchFragment = SearchFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerFL, searchFragment, "FRAGMENT_SEARCH")
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun removeSearch() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.remove(searchFragment).commitNowAllowingStateLoss()
    }

    private fun showChangeLanguageDialog() {
        val checkedItem: Int = if (isIndonesian(languageManager.getMyLang())) {
            1
        } else {
            0
        }
        val listItems = arrayOf(getString(R.string.english), getString(R.string.indonesian))
        val mBuilder = AlertDialog.Builder(this@HomeActivity)
        mBuilder.setTitle(getString(R.string.choose_language))
        mBuilder.setSingleChoiceItems(listItems, checkedItem) { dialogInterface, i ->
            if (i == 0) {
                languageManager.setLocale("en")
                reopen()
            } else if (i == 1) {
                languageManager.setLocale("in")
                reopen()
            }
            dialogInterface.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun setupViewPager(viewPager: ViewPager) {
        homeViewPagerAdapter = HomeViewPagerAdapter(supportFragmentManager)
        homeViewPagerAdapter.addFragment(MovieFragment(), getString(R.string.movie))
        homeViewPagerAdapter.addFragment(TvSeriesFragment(), getString(R.string.series))
        homeViewPagerAdapter.addFragment(FavoriteFragment(), getString(R.string.favorite))
        viewPager.adapter = homeViewPagerAdapter
    }
}