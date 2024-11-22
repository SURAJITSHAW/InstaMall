package com.example.instamall.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instamall.databinding.ItemSizeBinding
class SizesAdapter(private val sizes: List<String>) : RecyclerView.Adapter<SizesAdapter.SizeViewHolder>() {

    inner class SizeViewHolder(private val binding: ItemSizeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(size: String) {
            binding.sizeText.text = size // Dynamically set the size text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeViewHolder {
        val binding = ItemSizeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SizeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        holder.bind(sizes[position]) // Set the size dynamically
    }

    override fun getItemCount(): Int = sizes.size
}

