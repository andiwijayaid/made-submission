package andi.android.madegdk.ui.home.movie.adapter

import andi.android.madegdk.R
import andi.android.madegdk.model.Movie
import andi.android.madegdk.utils.convertRatingToFloat
import andi.android.madegdk.utils.getDrawableId
import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide


class MovieAdapter(private val context: Context?, private var movies: ArrayList<Movie>, private val listener: (Movie) -> Unit) :
        RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MovieViewHolder {
        return MovieViewHolder(
                LayoutInflater.from(p0.context).inflate(
                        R.layout.item_movie,
                        p0,
                        false
                )
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(p0: MovieViewHolder, p1: Int) {
        if (context != null) {
            p0.bindItem(context, movies[p1], listener)
        }
    }

}

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val titleTV: TextView = view.findViewById(R.id.titleTV)
    val dateTV: TextView = view.findViewById(R.id.dateTV)
    val posterIV: ImageView = view.findViewById(R.id.posterIV)
    val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
    val runtimeTV: TextView = view.findViewById(R.id.runtimeTV)
    val posterCV: CardView = view.findViewById(R.id.posterCV)

    @SuppressLint("SetTextI18n")
    fun bindItem(context: Context, movie: Movie, listener: (Movie) -> Unit) {
        titleTV.setText(movie.title)
        dateTV.setText(movie.date)
        runtimeTV.setText("${movie.runtime} min")
        Glide.with(context)
                .load(getDrawableId(context, movie.poster))
                .into(posterIV)
        ratingBar.rating = convertRatingToFloat(movie.rating)

        itemView.setOnClickListener {
            listener(movie)
        }
    }
}