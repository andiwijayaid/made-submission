package andi.android.madegdk.ui.home

import andi.android.madegdk.R
import andi.android.madegdk.ui.home.favorite.FavoriteFragment
import andi.android.madegdk.ui.home.movie.MovieFragment
import andi.android.madegdk.ui.home.tvseries.TvSeriesFragment
import andi.android.madegdk.utils.LanguageManager
import andi.android.madegdk.utils.isIndonesian
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private lateinit var languageManager: LanguageManager

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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        if (isIndonesian(languageManager.getMyLang())) {
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_indonesian)
        } else {
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_english)
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
        }
        return super.onOptionsItemSelected(item)
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