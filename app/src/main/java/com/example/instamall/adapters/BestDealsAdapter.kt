package com.example.instamall.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instamall.R
import com.example.instamall.data.Product
import com.example.instamall.fragments.HomeFragmentDirections

class BestDealsAdapter(
    private val deals: List<Product>,
    private val navController: NavController, // Add NavController to handle navigation
    private val onAddToCartClicked: (Product) -> Unit, // Callback for button click
) : RecyclerView.Adapter<BestDealsAdapter.DealViewHolder>() {

    // ViewHolder class
    class DealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dealTitle: TextView = itemView.findViewById(R.id.deal_title)
        val dealPrice: TextView = itemView.findViewById(R.id.deal_price)
        val dealImage: ImageView = itemView.findViewById(R.id.dealsImg)
        val addToCartButton: Button = itemView.findViewById(R.id.add_to_cart_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_best_deals, parent, false) // Use the XML layout provided
        return DealViewHolder(view)
    }

    override fun onBindViewHolder(holder: DealViewHolder, position: Int) {
        val deal = deals[position]

        // Set the title
        holder.dealTitle.text = deal.name

        // Format price to include ₹ and remove decimals
        val formattedPrice = "₹${deal.price.toInt()}" // Convert price to an integer to remove decimals
        holder.dealPrice.text = formattedPrice

        // Load image using Glide or any image loader
        Glide.with(holder.itemView.context)
            .load(deal.imageUrls[1])
            .into(holder.dealImage)

        // Navigate to ProductDetailsFragment on deal click
        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(deal)
            navController.navigate(action)
        }

        // Add to Cart button click
        holder.addToCartButton.setOnClickListener {
            onAddToCartClicked(deal)
        }
    }

    override fun getItemCount(): Int = deals.size
}
