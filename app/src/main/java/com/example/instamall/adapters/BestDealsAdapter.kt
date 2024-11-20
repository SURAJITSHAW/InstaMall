package com.example.instamall.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instamall.R
import com.example.instamall.data.Product

class BestDealsAdapter (
        private val deals: List<Product>
    ) : RecyclerView.Adapter<BestDealsAdapter.DealViewHolder>() {

        // ViewHolder class
        class DealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val dealTitle: TextView = itemView.findViewById(R.id.deal_title)
            val dealPrice: TextView = itemView.findViewById(R.id.deal_price)
            val dealImage: ImageView = itemView.findViewById(R.id.dealsImg)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_best_deals, parent, false) // Use the XML layout provided
            return DealViewHolder(view)
        }

        override fun onBindViewHolder(holder: DealViewHolder, position: Int) {
            val deal = deals[position]
            holder.dealTitle.text = deal.name
            holder.dealPrice.text = "Rs."+deal.price.toString()

            // Load image using Glide or any image loader
            Glide.with(holder.itemView.context)
                .load(deal.imageUrls[0])
                .into(holder.dealImage)
        }

        override fun getItemCount(): Int = deals.size
    }