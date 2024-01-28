package com.example.akrili.Adapter




import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.akrili.Data.Posts
import com.example.akrili.Data.imageslist
import com.example.akrili.R
import com.example.akrili.homepage



class MyAdapterImagePost(private val imageslist: imageslist) : RecyclerView.Adapter<MyAdapterImagePost.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.imagepostitems, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Load each image URL into the corresponding ImageView
        when (position) {
            0 -> Glide.with(holder.itemView).load(imageslist.image0).into(holder.imageView)
            1 -> Glide.with(holder.itemView).load(imageslist.image1).into(holder.imageView1)
            2 -> Glide.with(holder.itemView).load(imageslist.image2).into(holder.imageView2)
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewforPost)
        val imageView1: ImageView = itemView.findViewById(R.id.imageViewforPost2)
        val imageView2: ImageView = itemView.findViewById(R.id.imageViewforPost3)
    }
}
