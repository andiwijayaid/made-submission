package andi.android.madegdk.ui.home.movie.adapter

import andi.android.madegdk.BuildConfig
import andi.android.madegdk.R
import andi.android.madegdk.model.Movie
import andi.android.madegdk.utils.normalizeRating
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*


class MovieAdapter(private val context: Context?, private val listener: (Movie, Int) -> Unit) :
        RecyclerView.Adapter<MovieViewHolder>() {

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

    fun getMovies(): ArrayList<Movie> {
        return mData
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

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindItem(context: Context, movie: Movie, listener: (Movie, Int) -> Unit) {
        itemView.itemParentCV.animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_slide_from_left)
        itemView.titleTV.text = movie.title
        itemView.dateTV.text = movie.date
        Glide.with(context)
                .load("${BuildConfig.IMAGE_URL}t/p/w185${movie.poster}")
                .into(itemView.posterIV)
        itemView.ratingBar.rating = normalizeRating(movie.rating)

        itemView.setOnClickListener {
            listener(movie, layoutPosition)
        }
    }
}