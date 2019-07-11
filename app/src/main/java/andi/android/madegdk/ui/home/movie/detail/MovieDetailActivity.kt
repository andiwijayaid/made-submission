package andi.android.madegdk.ui.home.movie.detail

import andi.android.madegdk.BuildConfig
import andi.android.madegdk.R
import andi.android.madegdk.model.Movie
import andi.android.madegdk.response.MovieDetailResponse
import andi.android.madegdk.utils.*
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.View {
    override fun onFail() {
        stopLoading()
        Toast.makeText(applicationContext, resources.getString(R.string.check_your_connection), Toast.LENGTH_LONG).show()
    }

    override fun onMovieDetailRetrieved(movieDetailResponse: MovieDetailResponse) {
        if (isIndonesian(languageManager.getMyLang())) {
            movieDetailResponse.budget = convertToRupiah(movieDetailResponse.budget.toString())
            movieDetailResponse.revenue = convertToRupiah(movieDetailResponse.revenue.toString())
        }
        movieDetailResponse.budget = convertToCurrency(movieDetailResponse.budget)
        movieDetailResponse.revenue = convertToCurrency(movieDetailResponse.revenue)
        if (!isZero(movieDetailResponse.budget)) {
            budgetTV.text = movieDetailResponse.budget
        }
        if (!isZero(movieDetailResponse.revenue)) {
            revenueTV.text = movieDetailResponse.revenue
        }
        numberOfSeasonTV.text = movieDetailResponse.runtime.toString()
        stopLoading()
    }

    private fun stopLoading() {
        budgetTV.visibility = View.VISIBLE
        revenueTV.visibility = View.VISIBLE
        numberOfSeasonTV.visibility = View.VISIBLE

        budgetPB.visibility = View.GONE
        revenuePB.visibility = View.GONE
        numberOfSeasonPB.visibility = View.GONE
    }

    private val extraMovie = "EXTRA_MOVIE"

    private lateinit var presenter: MovieDetailPresenter
    private lateinit var languageManager: LanguageManager

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        languageManager = LanguageManager(this)

        initUI()

        val movie = intent.getParcelableExtra<Movie>(extraMovie)
        titleTV.text = movie.title
        dateTV.text = movie.date
        if (movie.overview != ""){
            overviewTV.text = movie.overview + "\n"
        }
        ratingBar.rating = normalizeRating(movie.rating)

        budgetTV.visibility = View.GONE
        revenueTV.visibility = View.GONE
        numberOfSeasonTV.visibility = View.INVISIBLE

        Glide.with(this)
                .load("${BuildConfig.IMAGE_URL}t/p/w500${movie.poster}")
                .into(posterIV)
        Glide.with(this)
                .load("${BuildConfig.IMAGE_URL}t/p/original${movie.backdrop}")
                .into(posterBackgroundIV)

        posterBackgroundIV.animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)

        presenter = MovieDetailPresenter(this)
        presenter.getMovieDetail(movie.id, resources.getString(R.string.language_code))

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
    }
}
