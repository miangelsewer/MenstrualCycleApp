package com.example.flixsterplusapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class FlixsterPlusRecyclerViewAdapter (
    private val movies: List<FlixsterPlus>,
    private val mListener: FlixsterPlusFragment
): RecyclerView.Adapter<FlixsterPlusRecyclerViewAdapter.MovieViewHolder>() {

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.fragment_flixster_plus, parent, false)
    return MovieViewHolder(view)
}
inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
    var mItem: FlixsterPlus? = null

    val mMovieName: TextView = mView.findViewById(R.id.movie_name)
    val mMovieImage: ImageView = mView.findViewById(R.id.movie_image)
    val mMovieDescription: TextView = mView.findViewById(R.id.movie_description)

    override fun toString(): String {
        return mMovieName.toString() + " '" + mMovieDescription.text + "'"
    }
}

override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    val movie = movies[position]

    holder.mItem = movie
    holder.mMovieName.text = movie.name
    holder.mMovieDescription.text = movie.description


    val imageUrl = movie.imageUrl
    Glide.with(holder.mView)
        .load(imageUrl)
        .centerInside()
        .into(holder.mMovieImage)

    // Sets up click listener for this park item
    holder.mView.setOnClickListener {
        holder.mItem?.let { movie ->
            mListener?.onItemClick(movie)
        }
    }
}

// Tells the RecyclerView how many items to display
override fun getItemCount(): Int {
    return movies.size
}}

