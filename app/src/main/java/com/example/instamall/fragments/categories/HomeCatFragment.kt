package com.example.instamall.fragments.categories

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instamall.R
import com.example.instamall.adapters.BestDealsAdapter
import com.example.instamall.adapters.BestProductsAdapter
import com.example.instamall.adapters.ImageSliderAdapter
import com.example.instamall.adapters.SpecialAdapter
import com.example.instamall.databinding.FragmentHomeCatBinding
import com.example.instamall.repo.ProductRepository
import com.example.instamall.utils.toggleBottomNav

class HomeCatFragment : Fragment() {

    private lateinit var binding: FragmentHomeCatBinding
    private val productRepository = ProductRepository()


    private lateinit var sliderAdapter: ImageSliderAdapter
    private lateinit var handler: Handler
    private var currentPosition = 0
    private val sliderImages = listOf(R.drawable.b1, R.drawable.b2, R.drawable.b3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeCatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.rvSpecial.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvBestDeals.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        binding.rvBestProducts.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        // Setup Image Slider
        setupImageSlider()

//        fetchSpecialProducts()
        fetchBestDealsProducts()
        fetchBestProducts()
    }

    private fun fetchBestProducts() {     
        productRepository.getBestDealsProducts(
        onSuccess = { products ->
            binding.rvBestProducts.adapter = BestProductsAdapter(
                products
            ) { product ->
                // Handle Add to Cart click
                Toast.makeText(requireContext(), "${product.name} added to cart", Toast.LENGTH_SHORT).show()
            }
        },
        onFailure = { exception ->
            Toast.makeText(requireContext(), "Failed to fetch products: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    )
    }

    private fun fetchBestDealsProducts() {
        productRepository.getBestDealsProducts(
            onSuccess = { products ->
                binding.rvBestDeals.adapter = BestDealsAdapter(products)
            },
            onFailure = { exception ->
                Toast.makeText(requireContext(), "Failed to fetch products: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }


    private fun setupImageSlider() {
        sliderAdapter = ImageSliderAdapter(requireContext(), sliderImages)
        binding.imageSlider.adapter = sliderAdapter

        // Auto-slide logic
        handler = Handler(Looper.getMainLooper())
        startAutoSlide()
    }

    private fun startAutoSlide() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                currentPosition = (currentPosition + 1) % sliderImages.size
                binding.imageSlider.currentItem = currentPosition
                handler.postDelayed(this, 3000) // Slide every 3 seconds
            }
        }, 3000)
    }

//    private fun fetchSpecialProducts() {
//        productRepository.getSpecialProducts(
//            onSuccess = { products ->
//                binding.rvSpecial.adapter = SpecialAdapter(products, findNavController())
//            },
//            onFailure = { exception ->
//                Toast.makeText(requireContext(), "Failed to fetch products: ${exception.message}", Toast.LENGTH_SHORT).show()
//            }
//        )
//    }

    override fun onResume() {
        super.onResume()
        activity.toggleBottomNav(isVisible = true, bottomNavId = R.id.bottomNavView)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null) // Stop auto-sliding to prevent memory leaks
    }

}