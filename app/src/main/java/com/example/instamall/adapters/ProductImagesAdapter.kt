package com.example.instamall.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instamall.databinding.ItemProductImageBinding

class ProductImagesAdapter(private val imageUrls: List<String>) : RecyclerView.Adapter<ProductImagesAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(private val binding: ItemProductImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            // Use Glide to load the image into the ImageView
            Glide.with(binding.productImage.context)
                .load(imageUrl)
                .into(binding.productImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemProductImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageUrls[position])
    }

    override fun getItemCount(): Int = imageUrls.size
}
