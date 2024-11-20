package com.example.instamall.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instamall.R
import com.example.instamall.data.Product

class BestProductsAdapter(
    private val products: List<Product>,
    private val onAddToCartClicked: (Product) -> Unit // Callback for button click
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
            holder.productPrice.text = product.price.toString()

            // Load image using Glide or any image loader
            Glide.with(holder.itemView.context)
                .load(product.imageUrls[0])
                .into(holder.productImage)

            // Add to Cart button click
            holder.addToCartButton.setOnClickListener {
                onAddToCartClicked(product)
            }
        }

        override fun getItemCount(): Int = products.size
    }