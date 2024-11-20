package com.example.instamall.fragments.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instamall.R
import com.example.instamall.adapters.SpecialAdapter
import com.example.instamall.databinding.FragmentHomeCatBinding
import com.example.instamall.repo.ProductRepository

class HomeCatFragment : Fragment() {

    private lateinit var binding: FragmentHomeCatBinding
    private val productRepository = ProductRepository()

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
        binding.rvSpecial.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        fetchSpecialProducts()
    }

    private fun fetchSpecialProducts() {
        productRepository.getSpecialProducts(
            onSuccess = { products ->
                binding.rvSpecial.adapter = SpecialAdapter(products)
            },
            onFailure = { exception ->
                Toast.makeText(requireContext(), "Failed to fetch products: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

}