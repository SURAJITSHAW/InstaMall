package com.example.instamall.adapters

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instamall.databinding.ItemColorBinding


class ColorsAdapter(private val colors: List<String>) : RecyclerView.Adapter<ColorsAdapter.ColorViewHolder>() {

    inner class ColorViewHolder(private val binding: ItemColorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(color: String) {
            // Set the background color for the inner view (the circle)
            val colorDrawable = binding.colorView.background as GradientDrawable
            colorDrawable.setColor(android.graphics.Color.parseColor(color))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val binding = ItemColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(colors[position])
    }

    override fun getItemCount(): Int = colors.size
}


