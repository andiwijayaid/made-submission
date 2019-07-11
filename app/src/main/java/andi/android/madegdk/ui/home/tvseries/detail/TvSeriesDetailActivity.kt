package andi.android.madegdk.ui.home.tvseries.detail

import andi.android.madegdk.BuildConfig
import andi.android.madegdk.R
import andi.android.madegdk.model.TvSeries
import andi.android.madegdk.response.TvSeriesDetailResponse
import andi.android.madegdk.utils.normalizeRating
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_tv_series_detail.*

class TvSeriesDetailActivity : AppCompatActivity(), TvSeriesDetailContract.View {
    override fun stopLoading() {

        numberOfSeasonTV.visibility = View.VISIBLE
        numberOfEpsTV.visibility = View.VISIBLE

        numberOfSeasonPB.visibility = View.INVISIBLE
        numberOfEpsPB.visibility = View.INVISIBLE

    }

    override fun onFail() {
        stopLoading()
        Toast.makeText(applicationContext, resources.getString(R.string.check_your_connection), Toast.LENGTH_LONG).show()
    }

    override fun onTvSeriesDetailRetrieved(tvSeriesDetailResponse: TvSeriesDetailResponse) {
        stopLoading()
        numberOfSeasonTV.text = tvSeriesDetailResponse.numberOfSeasons
        numberOfEpsTV.text = tvSeriesDetailResponse.numberOfEpisodes
    }

    private val extraMovie = "EXTRA_TV_SERIES"
    private lateinit var presenter: TvSeriesDetailPresenter

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

        numberOfSeasonTV.visibility = View.INVISIBLE
        numberOfEpsTV.visibility = View.INVISIBLE

        Glide.with(this)
                .load("${BuildConfig.IMAGE_URL}t/p/original${tvSeries.poster}")
                .into(posterIV)
        Glide.with(this)
                .load("${BuildConfig.IMAGE_URL}t/p/original${tvSeries.backdrop}")
                .into(posterBackgroundIV)

        posterBackgroundIV.animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)

        presenter = TvSeriesDetailPresenter(this)
        presenter.getTvSeriesDetail(tvSeries.id, resources.getString(R.string.language_code))
    }
}
