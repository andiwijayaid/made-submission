package andi.android.madegdk.ui.home.tvseries.detail

import andi.android.madegdk.R
import andi.android.madegdk.model.TvSeries
import andi.android.madegdk.utils.getDrawableId
import andi.android.madegdk.utils.normalizeRating
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.view.animation.AnimationUtils
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
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        toolbar.setNavigationOnClickListener { finish() }

        val tvSeries = intent.getParcelableExtra<TvSeries>(extraMovie)
        titleTV.text = tvSeries.title
        dateTV.text = tvSeries.firstAirDate
        ratingBar.rating = normalizeRating(tvSeries.rating)
        overviewTV.text = "${tvSeries.overview}\n"

        Glide.with(this)
                .load(getDrawableId(applicationContext, tvSeries.poster.toString()))
                .into(posterIV)
        Glide.with(this)
                .load(getDrawableId(applicationContext, tvSeries.poster.toString()))
                .into(posterBackgroundIV)

        watchBT.setOnClickListener { Toast.makeText(applicationContext, getString(R.string.watching), Toast.LENGTH_LONG).show() }

        posterBackgroundIV.animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
    }
}
