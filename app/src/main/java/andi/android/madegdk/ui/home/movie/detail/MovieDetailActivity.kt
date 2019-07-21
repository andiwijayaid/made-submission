package andi.android.madegdk.ui.home.movie.detail

import andi.android.madegdk.BuildConfig
import andi.android.madegdk.R
import andi.android.madegdk.database.FavoriteMovieHelper
import andi.android.madegdk.model.Movie
import andi.android.madegdk.response.MovieDetailResponse
import andi.android.madegdk.utils.*
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    private fun stopLoading() {
        budgetTV.visibility = View.VISIBLE
        revenueTV.visibility = View.VISIBLE
        numberOfSeasonTV.visibility = View.VISIBLE

        budgetPB.visibility = View.GONE
        revenuePB.visibility = View.GONE
        numberOfSeasonPB.visibility = View.GONE
    }

    private val extraMovie = "EXTRA_MOVIE"
    private val extraPosition = "EXTRA_POSITION"
    private val extraIsFavorite = "IS_FAVORITE"

    private val RESULT_FAVORITE = 998

    private var position = -1

    private lateinit var languageManager: LanguageManager

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        languageManager = LanguageManager(this)
        languageManager.loadLocale()
        setContentView(R.layout.activity_movie_detail)

        initUI()

        position = intent.getIntExtra(extraPosition, position)

        val movie = intent.getParcelableExtra<Movie>(extraMovie)
        titleTV.text = movie.title
        dateTV.text = movie.date
        if (movie.overview != "") {
            overviewTV.text = String.format(resources.getString(R.string.overview_format), movie.overview)
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

        favoriteBT.isChecked = intent.getBooleanExtra(extraIsFavorite, true)

        movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
        if (!movieDetailViewModel.isMovieRetrieved()) {
            movieDetailViewModel.setMovie(movie.movieId, resources.getString(R.string.language_code))
        }
        movieDetailViewModel.getMovie()?.observe(this, getMovie)

        val favoriteMovieHelper = FavoriteMovieHelper.getInstance(applicationContext)
        favoriteBT.setOnClickListener {
            if (favoriteBT.isChecked) {
                val aMovie = Movie(movie.movieId, movie.poster, movie.backdrop, movie.title, movie.date, movie.rating, movie.overview)
                favoriteMovieHelper?.insertMovieFavorite(aMovie)
                Log.d("FAV", favoriteMovieHelper?.getAllFavoriteMovies().toString())
            } else {
                favoriteMovieHelper?.deleteMovieFavorite(movie.movieId)
                Log.d("FAV", favoriteMovieHelper?.getAllFavoriteMovies().toString())
            }
            sendResult()
        }
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

    private val getMovie = Observer<MovieDetailResponse> {
        if (it != null) {
            var mBudget = it.budget
            var mRevenue = it.revenue
            if (isIndonesian(languageManager.getMyLang())) {
                mBudget = convertToRupiah(it.budget.toString())
                mRevenue = convertToRupiah(it.revenue.toString())
            }
            mBudget = convertToCurrency(mBudget)
            mRevenue = convertToCurrency(mRevenue)
            if (!isZero(it.budget)) {
                budgetTV.text = mBudget
            }
            if (!isZero(it.revenue)) {
                revenueTV.text = mRevenue
            }

            numberOfSeasonTV.text = it.runtime.toString()
            stopLoading()
        } else {
            stopLoading()
            Toast.makeText(applicationContext, resources.getString(R.string.check_your_connection), Toast.LENGTH_LONG).show()
        }
    }

    fun sendResult() {
        val intent = Intent()
        intent.putExtra(extraPosition, position)
        setResult(RESULT_FAVORITE, intent)
    }
}
