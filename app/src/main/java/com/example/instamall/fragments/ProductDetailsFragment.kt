package com.example.instamall.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instamall.R
import com.example.instamall.adapters.ColorsAdapter
import com.example.instamall.adapters.ProductImagesAdapter
import com.example.instamall.adapters.SizesAdapter
import com.example.instamall.data.Product
import com.example.instamall.databinding.FragmentProductDetailsBinding
import com.example.instamall.utils.toggleBottomNav

class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var colorsAdapter: ColorsAdapter
    private lateinit var sizesAdapter: SizesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hide Bottom Navigation
        activity.toggleBottomNav(isVisible = false, bottomNavId = R.id.bottomNavView)


        // Retrieve product from SafeArgs
        val product: Product = ProductDetailsFragmentArgs.fromBundle(requireArguments()).product

        // Set product details
        binding.productName.text = product.name
        binding.productPrice.text = "$${product.price}"
        binding.productDescription.text = product.description ?: "No description available."

        // Set up ViewPager for product images
        setupViewPager(product.imageUrls)
// Initialize RecyclerViews for colors and sizes
        if (!product.colors.isNullOrEmpty()) {
            setupColorsRecyclerView(product.colors)
        }

        if (!product.sizes.isNullOrEmpty()) {
            setupSizesRecyclerView(product.sizes)
        }


        // Add to cart button click listener
        binding.addToCartButton.setOnClickListener {
            // Add your Add-to-Cart logic here
        }
    }

    private fun setupViewPager(imageUrls: List<String>) {
        val productImagesAdapter = ProductImagesAdapter(imageUrls)
        binding.viewPager.adapter = productImagesAdapter
    }

    private fun setupColorsRecyclerView(colors: List<String>?) {
        colorsAdapter = ColorsAdapter(colors ?: emptyList())
        binding.colorsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = colorsAdapter
        }
    }

    private fun setupSizesRecyclerView(sizes: List<String>?) {
        sizesAdapter = SizesAdapter(sizes ?: emptyList())
        binding.sizesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = sizesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}