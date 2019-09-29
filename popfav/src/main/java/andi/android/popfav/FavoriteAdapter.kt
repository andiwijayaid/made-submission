package andi.android.popfav

import andi.android.popfav.model.Movie
import andi.android.popfav.util.normalizeRating
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*

class FavoriteAdapter(private val context: Context?, private val listener: (Movie, Int) -> Unit) : RecyclerView.Adapter<FavoriteViewHolder>() {

    private val favorites = arrayListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_movie,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int = favorites.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        if (context != null) {
            holder.bindItem(context, favorites[position], listener)
        }
    }

    fun getFavorites(): ArrayList<Movie> {
        return favorites
    }

    fun setFavorites(favorites: ArrayList<Movie>) {
        this.favorites.clear()
        this.favorites.addAll(favorites)
        notifyDataSetChanged()
    }
}

class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindItem(context: Context, movie: Movie, listener: (Movie, Int) -> Unit) {
        Glide.with(context)
                .load("${BuildConfig.IMAGE_URL}t/p/w185${movie.poster}")
                .into(itemView.posterIV)
        itemView.itemParentCV.animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_slide_from_left)
        itemView.titleTV.text = movie.title
        itemView.dateTV.text = movie.date
        itemView.ratingBar.rating = normalizeRating(movie.rating)

        itemView.setOnClickListener {
            listener(movie, layoutPosition)
        }
    }
}