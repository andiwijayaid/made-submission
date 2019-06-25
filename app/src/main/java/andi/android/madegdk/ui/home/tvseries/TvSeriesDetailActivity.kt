package andi.android.madegdk.ui.home.tvseries

import andi.android.madegdk.R
import andi.android.madegdk.model.TvSeries
import andi.android.madegdk.utils.convertRatingToFloat
import andi.android.madegdk.utils.getDrawableId
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_tv_series_detail.*

class TvSeriesDetailActivity : AppCompatActivity() {

    private val extraMovie = "EXTRA_TV_SERIES"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_series_detail)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp)
        toolbar.setNavigationOnClickListener { finish() }

        val tvSeries = intent.getParcelableExtra<TvSeries>(extraMovie)
        titleTV.text = tvSeries.title
        dateTV.text = tvSeries.date
        ratingBar.rating = convertRatingToFloat(tvSeries.rating)
        overviewTV.text = "${tvSeries.overview}\n"
        runtimeTV.text = tvSeries.runtime.toString()
        numberOfEpsTV.text = tvSeries.numberOfEpisode.toString()

        Glide.with(this)
                .load(getDrawableId(applicationContext, tvSeries.poster.toString()))
                .into(posterIV)
        Glide.with(this)
                .load(getDrawableId(applicationContext, tvSeries.poster.toString()))
                .into(posterBackgroundIV)

        watchBT.setOnClickListener { Toast.makeText(applicationContext, getString(R.string.watching), Toast.LENGTH_LONG).show() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
