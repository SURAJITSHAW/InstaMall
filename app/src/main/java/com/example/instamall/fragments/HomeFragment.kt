package com.example.instamall.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instamall.R
import com.example.instamall.adapters.HomeViewPagerAdapter
import com.example.instamall.databinding.FragmentHomeBinding
import com.example.instamall.fragments.categories.AccessoriesCatFragment
import com.example.instamall.fragments.categories.CardioCatFragment
import com.example.instamall.fragments.categories.FlexibilityCatFragment
import com.example.instamall.fragments.categories.HomeCatFragment
import com.example.instamall.fragments.categories.StrengthTrainingCatFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryFragments = arrayListOf<Fragment>(
            HomeCatFragment(),
            CardioCatFragment(),
            FlexibilityCatFragment(),
            StrengthTrainingCatFragment(),
            AccessoriesCatFragment()
        )

        val homeViewPagerAdapter = HomeViewPagerAdapter(categoryFragments, childFragmentManager, lifecycle)
        binding.viewPager.adapter = homeViewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) {
            tab, position ->
                tab.text = when(position) {
                    0 -> "Home"
                    1 -> "Cardio"
                    2 -> "Flexibility"
                    3 -> "Training"
                    4 -> "Accessory"
                    else -> null
                }
        }.attach()
    }

}