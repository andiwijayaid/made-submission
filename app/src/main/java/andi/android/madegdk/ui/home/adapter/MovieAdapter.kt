package andi.android.madegdk.ui.home.adapter

import andi.android.madegdk.R
import andi.android.madegdk.model.Movie
import andi.android.madegdk.utils.convertRatingToFloat
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import java.util.*

class MovieAdapter(private val context: Context) : BaseAdapter() {
    private var movies: ArrayList<Movie>? = null

    init {
        movies = ArrayList()
    }

    fun setMovies(movies: ArrayList<Movie>) {
        this.movies = movies
    }

    override fun getCount(): Int {
        return movies?.size!!
    }

    override fun getItem(position: Int): Movie? {
        return movies?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var mConvertView = convertView
        if (convertView == null) {
            mConvertView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)

            val viewHolder = ViewHolder(convertView)
            val movie = getItem(position)
            viewHolder.bind(movie!!)
        }
        return mConvertView
    }

    private inner class ViewHolder(view: View?) {
        private val titleTV = view?.findViewById<TextView>(R.id.titleTV)
        private val dateTV = view?.findViewById<TextView>(R.id.dateTV)
        private val runtimeTV = view?.findViewById<TextView>(R.id.runtimeTV)
        private val posterIV = view?.findViewById<ImageView>(R.id.posterIV)
        private val ratingBar = view?.findViewById<RatingBar>(R.id.ratingBar)

        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie) {
            titleTV?.text = "A"
            dateTV?.text = movie.date
            runtimeTV?.text = movie.runtime!!.toString() + " min"
            if (posterIV != null) {
                Glide.with(context)
                        .load(getDrawableId(movie.poster))
                        .into(posterIV)
            }
            ratingBar?.rating = convertRatingToFloat(movie.rating)
        }
    }

    private fun getDrawableId(drawableName: String?): Int {
        return context.resources.getIdentifier(drawableName, "drawable", context.packageName)
    }
}