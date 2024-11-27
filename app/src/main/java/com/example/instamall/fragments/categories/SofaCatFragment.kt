package com.example.instamall.fragments.categories

import com.example.instamall.databinding.FragmentHomeCatBinding
import com.example.instamall.repo.ProductRepository

class SofaCatFragment : BaseCatFragment() {


    override fun getCategoryId(): String {
        return "sofas" // The category ID for sofas
    }

    override fun getHeading(): String {
        return "Sofas" // The heading for the sofa category
    }

}
