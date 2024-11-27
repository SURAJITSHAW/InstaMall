package com.example.instamall.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instamall.adapters.ProductAdapter
import com.example.instamall.data.Product
import com.example.instamall.databinding.FragmentBaseCatBinding
import com.example.instamall.repo.ProductRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.UUID

abstract class BaseCatFragment : Fragment() {
    private var _binding: FragmentBaseCatBinding? = null
    private val binding get() = _binding!!


    private val firestore by lazy { FirebaseFirestore.getInstance() }
    private val currentUser by lazy { FirebaseAuth.getInstance().currentUser }

    private val productRepository = ProductRepository()

    // Child fragments will override these methods
    abstract fun getCategoryId(): String
    abstract fun getHeading(): String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBaseCatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the heading
        binding.tvHeading.text = getHeading()

        // Setup RecyclerView
        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)

        // Fetch and display products
        fetchProducts()
    }


    private fun addToCart(product: Product) {
        currentUser?.let { user ->
            val cartItem = hashMapOf(
                "product" to product, // Stores the entire Product object
                "quantity" to 1 // Default quantity
            )
            val cartRef = firestore.collection("users")
                .document(user.uid)
                .collection("cart")

            // Add the product to the user's cart
            cartRef.document(product.id ?: UUID.randomUUID().toString())
                .set(cartItem, SetOptions.merge())
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Added to cart", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Failed to add to cart: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } ?: Toast.makeText(requireContext(), "Please log in to add items to the cart", Toast.LENGTH_SHORT).show()
    }


    protected open fun fetchProducts() {
        productRepository.getProductsByCategory(
            categoryId = getCategoryId(),
            onSuccess = { products ->
                binding.rvProducts.adapter = ProductAdapter(products, navController = findNavController(),
                ) { product ->
                    // Handle Add to Cart click
                    addToCart(product)
                    Toast.makeText(requireContext(), "${product.name} added to cart", Toast.LENGTH_SHORT).show()
                }
            },
            onFailure = { exception ->
                Toast.makeText(requireContext(), "Failed to load products: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
