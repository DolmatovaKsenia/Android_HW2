package com.example.myapplication_hw2_

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FoxAdapter(private var foxImages: List<String>) : RecyclerView.Adapter<FoxAdapter.FoxViewHolder>() {

    class FoxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoxViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_fox, parent, false)
        return FoxViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoxViewHolder, position: Int) {
        val imageUrl = foxImages[position]
        Glide.with(holder.imageView.context).load(imageUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return foxImages.size
    }

    fun updateFoxes(savedFoxes: List<String>) {
        foxImages = savedFoxes
        notifyDataSetChanged()
    }
}