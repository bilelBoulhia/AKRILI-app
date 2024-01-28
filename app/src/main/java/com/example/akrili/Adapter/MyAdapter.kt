package com.example.akrili.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.akrili.Data.Posts
import com.example.akrili.R
import com.example.akrili.homepage
import com.facebook.shimmer.ShimmerFrameLayout


class MyAdapter(private val PostsList: ArrayList<Posts>, private val clickListener: homepage) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.vertical_listing_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return PostsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val post = PostsList[position]
        holder.Title.text = post.title
        holder.Location.text = post.location
        holder.Phone.text = post.phone

        Glide.with(holder.itemView)
            .load(post.imageUrl)
            .into(holder.imageView)


        holder.itemView.setOnClickListener {
            clickListener.onItemClick(position)
        }

        if (isLoading) {
            adapter.startShimmerAnimation(holder.shimmerContainer)
        } else {
            adapter.stopShimmerAnimation(holder.shimmerContainer)
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

val Title : TextView = itemView.findViewById(R.id.Title)
        val Location: TextView = itemView.findViewById(R.id.Wilaya)
        val imageView: ImageView = itemView.findViewById(R.id.Image)
        val Phone: TextView = itemView.findViewById(R.id.Phone)

    }



    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun startShimmerAnimation(shimmerContainer: ShimmerFrameLayout) {
        shimmerContainer.startShimmer()
    }

    fun stopShimmerAnimation(shimmerContainer: ShimmerFrameLayout) {
        shimmerContainer.stopShimmer()
        shimmerContainer.visibility = View.GONE
    }


}