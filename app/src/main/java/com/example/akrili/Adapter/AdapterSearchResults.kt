package com.example.akrili.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.akrili.Data.Posts
import com.example.akrili.Data.searchpost
import com.example.akrili.R
import com.example.akrili.Searchsection
import com.example.akrili.homepage

class AdapterSearchResults(private val resultpost : ArrayList<searchpost>,private val clickListenersearch: Searchsection) : RecyclerView.Adapter<AdapterSearchResults.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.vertical_listing_item_searchresult,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterSearchResults.MyViewHolder, position: Int) {


        val post = resultpost[position]
        holder.TitleO.text = post.searchtitle
       holder.LocationO.text = post.searchlocation
        holder.phone0.text = post.searchphone

        Glide.with(holder.itemView)
            .load(post.searchimageUrl)
            .into(holder.imageViewO)
        holder.itemView.setOnClickListener {
            clickListenersearch.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return resultpost.size
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val TitleO : TextView = itemView.findViewById(R.id.Titleresult)
        val LocationO: TextView = itemView.findViewById(R.id.Wilayaresult)
        val imageViewO: ImageView = itemView.findViewById(R.id.Imageresult)
        val phone0: TextView = itemView.findViewById(R.id.Phoneresult)

    }
}