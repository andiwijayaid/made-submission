package andi.android.madegdk.ui.home.tvseries.detail

import andi.android.madegdk.BuildConfig
import andi.android.madegdk.R
import andi.android.madegdk.model.TvSeries
import andi.android.madegdk.response.TvSeriesDetailResponse
import andi.android.madegdk.utils.LanguageManager
import andi.android.madegdk.utils.normalizeRating
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_tv_series_detail.*

class TvSeriesDetailActivity : AppCompatActivity() {
    private fun stopLoading() {

        numberOfSeasonTV.visibility = View.VISIBLE
        numberOfEpsTV.visibility = View.VISIBLE

        numberOfSeasonPB.visibility = View.INVISIBLE
        numberOfEpsPB.visibility = View.INVISIBLE

    }

    private val extraMovie = "EXTRA_TV_SERIES"

    private lateinit var languageManager: LanguageManager

    private lateinit var tvSeriesDetailViewModel: TvSeriesDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        languageManager = LanguageManager(this)
        languageManager.loadLocale()
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
        if (tvSeries.overview != "") {
            overviewTV.text = String.format(resources.getString(R.string.overview_format), tvSeries.overview)
        }

        numberOfSeasonTV.visibility = View.INVISIBLE
        numberOfEpsTV.visibility = View.INVISIBLE

        Glide.with(this)
                .load("${BuildConfig.IMAGE_URL}t/p/original${tvSeries.poster}")
                .into(posterIV)
        Glide.with(this)
                .load("${BuildConfig.IMAGE_URL}t/p/original${tvSeries.backdrop}")
                .into(posterBackgroundIV)

        posterBackgroundIV.animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)

        tvSeriesDetailViewModel = ViewModelProviders.of(this).get(TvSeriesDetailViewModel::class.java)
        if (!tvSeriesDetailViewModel.isTvSeriesRetrieved()) {
            tvSeriesDetailViewModel.setTvSeries(tvSeries.id, resources.getString(R.string.language_code))
        }
        tvSeriesDetailViewModel.getTvSeries()?.observe(this, getTvSeries)
    }

    private val getTvSeries = Observer<TvSeriesDetailResponse> {
        if (it != null) {
            stopLoading()
            numberOfSeasonTV.text = it.numberOfSeasons
            numberOfEpsTV.text = it.numberOfEpisodes
        } else {
            stopLoading()
            Toast.makeText(applicationContext, resources.getString(R.string.check_your_connection), Toast.LENGTH_LONG).show()
        }
    }
}
