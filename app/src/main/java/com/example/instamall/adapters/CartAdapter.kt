package com.example.instamall.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instamall.data.CartItem
import com.example.instamall.databinding.CartItemAlyoutBinding
import com.bumptech.glide.Glide

class CartAdapter(
    private val cartItems: MutableList<CartItem>,
    private val onQuantityChanged: (Int, Int) -> Unit,  // Callback for quantity changes
    private val onRemoveItem: (Int) -> Unit             // Callback for item removal
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: CartItemAlyoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem, position: Int) {
            with(binding) {
                productName.text = item.product.name
                productPrice.text = "₹${item.product.price}"
                productQty.text = item.quantity.toString()

                // Load product image with Glide
                Glide.with(productImage.context)
                    .load(item.product.imageUrls[0])
                    .into(productImage)

                increaseQty.setOnClickListener {
                    onQuantityChanged(position, item.quantity + 1)
                }

                decreaseQty.setOnClickListener {
                    if (item.quantity > 1) {
                        onQuantityChanged(position, item.quantity - 1)
                    }
                }

                removeProduct.setOnClickListener {
                    onRemoveItem(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemAlyoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position], position)
    }

    override fun getItemCount(): Int = cartItems.size
}
