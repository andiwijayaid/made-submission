package andi.android.madegdk.ui.home.movie.adapter

import andi.android.madegdk.BuildConfig
import andi.android.madegdk.R
import andi.android.madegdk.model.Movie
import andi.android.madegdk.utils.normalizeRating
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide


class MovieAdapter(private val context: Context?, private val listener: (Movie) -> Unit) :
        androidx.recyclerview.widget.RecyclerView.Adapter<MovieViewHolder>() {

    private val mData = ArrayList<Movie>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MovieViewHolder {
        return MovieViewHolder(
                LayoutInflater.from(p0.context).inflate(
                        R.layout.item_movie,
                        p0,
                        false
                )
        )
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(p0: MovieViewHolder, p1: Int) {
        if (context != null) {
            p0.bindItem(context, mData[p1], listener)
        }
    }

    fun setMovies(movies: ArrayList<Movie>) {
        mData.clear()
        mData.addAll(movies)
        notifyDataSetChanged()
    }

    fun addMovies(movies: ArrayList<Movie>) {
        mData.addAll(movies)
        notifyDataSetChanged()
    }

}

class MovieViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
    private val titleTV: TextView = view.findViewById(R.id.titleTV)
    private val dateTV: TextView = view.findViewById(R.id.dateTV)
    private val posterIV: ImageView = view.findViewById(R.id.posterIV)
    private val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
    private val itemParentCV: CardView = view.findViewById(R.id.itemParentCV)

    @SuppressLint("SetTextI18n")
    fun bindItem(context: Context, movie: Movie, listener: (Movie) -> Unit) {
//        itemParentCV.animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_slide_from_bottom)
        titleTV.text = movie.title
        dateTV.text = movie.date
        Glide.with(context)
                .load("${BuildConfig.IMAGE_URL}t/p/w185${movie.poster}")
                .into(posterIV)
        ratingBar.rating = normalizeRating(movie.rating)

        itemView.setOnClickListener {
            listener(movie)
        }
    }
}