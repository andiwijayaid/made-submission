package andi.android.madegdk.ui.home.movie

import andi.android.madegdk.R
import andi.android.madegdk.model.Movie
import andi.android.madegdk.utils.convertRatingToFloat
import andi.android.madegdk.utils.convertToCurrency
import andi.android.madegdk.utils.getDrawableId
import andi.android.madegdk.utils.isZero
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    private val extraMovie = "EXTRA_MOVIE"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        initUI()

        val movie = intent.getParcelableExtra<Movie>(extraMovie)
        Log.d("DM", movie.title)
        titleTV.text = movie.title
        dateTV.text = movie.date
        overviewTV.text = movie.overview + "\n"
        ratingBar.rating = convertRatingToFloat(movie.rating)
        runtimeTV.text = movie.runtime.toString()

        if (isZero(movie.budget)) {
            budgetTV.text = getString(R.string.not_available_sign)
        } else {
            budgetTV.text = convertToCurrency(movie.budget)
        }

        if (isZero(movie.revenue)) {
            revenueTV.text = getString(R.string.not_available_sign)
        } else {
            revenueTV.text = convertToCurrency(movie.revenue)
        }

        Glide.with(this)
                .load(getDrawableId(applicationContext, movie.poster.toString()))
                .into(posterIV)
        Glide.with(this)
                .load(getDrawableId(applicationContext, movie.poster.toString()))
                .into(posterBackgroundIV)

        posterBackgroundIV.animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
    }

    private fun initUI() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        watchBT.setOnClickListener { Toast.makeText(applicationContext, getString(R.string.watching), Toast.LENGTH_LONG).show() }
    }
}
