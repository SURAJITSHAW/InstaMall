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

class BestProductsAdapter(
    private val products: List<Product>,
    private val navController: NavController, // Add NavController to handle navigation
    private val onAddToCartClicked: (Product) -> Unit, // Callback for button click
) : RecyclerView.Adapter<BestProductsAdapter.ProductViewHolder>() {

    // ViewHolder class
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val productImage: ImageView = itemView.findViewById(R.id.bestProductsimage)
        val addToCartButton: Button = itemView.findViewById(R.id.btn_add_to_cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_card, parent, false) // Use the XML layout provided
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.productName.text = product.name

        // Format price as Indian Rupees (₹) and remove decimal part
        holder.productPrice.text = "₹${product.price.toInt()}" // This ensures no decimals

        // Load image using Glide or any image loader
        Glide.with(holder.itemView.context)
            .load(product.imageUrls[0])
            .into(holder.productImage)

        // Add to Cart button click
        holder.addToCartButton.setOnClickListener {
            onAddToCartClicked(product)
        }

        // Navigate to ProductDetailsFragment on product click
        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(product)
            navController.navigate(action)
        }
    }

    override fun getItemCount(): Int = products.size
}
