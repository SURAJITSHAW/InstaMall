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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CartFragment : Fragment(R.layout.fragment_cart) {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartAdapter: CartAdapter
    private val cartItems = mutableListOf<CartItem>()

    private val firestore = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)

        setupRecyclerView()
        fetchCartItems()

        binding.checkoutButton.setOnClickListener {
            Toast.makeText(requireContext(), "Proceeding to Checkout", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            cartItems,
            onQuantityChanged = { position, newQty ->
                cartItems[position].quantity = newQty
                cartAdapter.notifyItemChanged(position)
                // Optional: Update Firestore with new quantity
                updateCartItemQuantity(cartItems[position].product.id!!, newQty)
            },
            onRemoveItem = { position ->
                val removedItem = cartItems[position]
                removeCartItem(removedItem.product.id!!)
                cartItems.removeAt(position)
                cartAdapter.notifyItemRemoved(position)
                Toast.makeText(requireContext(), "Item removed", Toast.LENGTH_SHORT).show()
            }
        )

        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    private fun fetchCartItems() {
        currentUser?.let { user ->
            val cartRef = firestore.collection("users")
                .document(user.uid)
                .collection("cart")

            cartRef.get()
                .addOnSuccessListener { querySnapshot ->
                    cartItems.clear()
                    for (document in querySnapshot) {
                        val product = document.get("product") as Map<String, Any>
                        val quantity = document.getLong("quantity")?.toInt() ?: 1

                        val cartItem = CartItem(
                            Product(
                                id = document.id,
                                name = product["name"].toString(),
                                category = product["category"].toString(),
                                price = (product["price"] as Number).toFloat(),
                                description = product["description"]?.toString(),
                                imageUrls = product["imageUrls"] as List<String>
                            ),
                            quantity
                        )
                        cartItems.add(cartItem)
                    }
                    cartAdapter.notifyDataSetChanged()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Failed to load cart items: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } ?: Toast.makeText(requireContext(), "Please log in to view the cart", Toast.LENGTH_SHORT).show()
    }

    private fun updateCartItemQuantity(productId: String, quantity: Int) {
        currentUser?.let { user ->
            val cartRef = firestore.collection("users")
                .document(user.uid)
                .collection("cart")
                .document(productId)

            cartRef.update("quantity", quantity)
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Failed to update quantity: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun removeCartItem(productId: String) {
        currentUser?.let { user ->
            val cartRef = firestore.collection("users")
                .document(user.uid)
                .collection("cart")
                .document(productId)

            cartRef.delete()
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Failed to remove item: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
