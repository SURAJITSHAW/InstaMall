package com.example.instamall.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instamall.data.Product
import com.example.instamall.databinding.ItemSpecialCardBinding
import com.example.instamall.fragments.HomeFragmentDirections

class SpecialAdapter(
    private val productList: List<Product>,
    private val navController: NavController // Add NavController to handle navigation
) : RecyclerView.Adapter<SpecialAdapter.SpecialViewHolder>() {

    class SpecialViewHolder(private val binding: ItemSpecialCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, navController: NavController) {
            binding.specialItemTitle.text = product.name
            binding.specialItemPrice.text = "Rs.${product.price}"

            // Load image using Glide
            Glide.with(binding.root).load(product.imageUrls[0]).into(binding.specialImg)

            // Open ProductDetailsFragment on item click
            binding.root.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(product)
                navController.navigate(action)
            }

            // Handle the heart button click
            binding.heartButton.setOnClickListener {
                // Add your favorite logic
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialViewHolder {
        val binding = ItemSpecialCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SpecialViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecialViewHolder, position: Int) {
        holder.bind(productList[position], navController)
    }

    override fun getItemCount(): Int = productList.size
}
