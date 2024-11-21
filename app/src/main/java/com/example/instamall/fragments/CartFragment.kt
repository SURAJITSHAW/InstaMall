package com.example.instamall.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instamall.R
import com.example.instamall.adapters.CartAdapter
import com.example.instamall.data.CartItem
import com.example.instamall.data.Product
import com.example.instamall.databinding.FragmentCartBinding

class CartFragment : Fragment(R.layout.fragment_cart) {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartAdapter: CartAdapter
    private val cartItems = mutableListOf(
        CartItem(Product("1", "Shirt", "Clothing", 499.99f, imageUrls = listOf("https://via.placeholder.com/150")), 2),
        CartItem(Product("2", "Jeans", "Clothing", 799.99f, imageUrls = listOf("https://via.placeholder.com/150")), 1)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)

        cartAdapter = CartAdapter(
            cartItems,
            onQuantityChanged = { position, newQty ->
                cartItems[position].quantity = newQty
                cartAdapter.notifyItemChanged(position)
            },
            onRemoveItem = { position ->
                cartItems.removeAt(position)
                cartAdapter.notifyItemRemoved(position)
                Toast.makeText(requireContext(), "Item removed", Toast.LENGTH_SHORT).show()
            }
        )

        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }

        binding.checkoutButton.setOnClickListener {
            Toast.makeText(requireContext(), "Proceeding to Checkout", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
