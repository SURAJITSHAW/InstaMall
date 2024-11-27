package com.example.instamall.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.instamall.R
import com.example.instamall.adapters.HomeViewPagerAdapter
import com.example.instamall.databinding.FragmentHomeBinding
import com.example.instamall.fragments.categories.SofaCatFragment
import com.example.instamall.fragments.categories.ChairsCatFragment
import com.example.instamall.fragments.categories.HomeCatFragment
import com.example.instamall.fragments.categories.CabinetsCatFragment
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
            SofaCatFragment(),
            ChairsCatFragment(),
            CabinetsCatFragment()

        )

        val homeViewPagerAdapter = HomeViewPagerAdapter(categoryFragments, childFragmentManager, lifecycle)
        binding.viewPager.adapter = homeViewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val customTab = LayoutInflater.from(requireContext()).inflate(R.layout.custom_tab, binding.tabLayout, false)
            val tabTitle = customTab.findViewById<TextView>(R.id.tab_title)
            tabTitle.text = when (position) {
                0 -> "Home"
                1 -> "Sofa"
                2 -> "Chairs"
                3 -> "Cabinets"
                else -> null
            }
            tab.customView = customTab
        }.attach()

    }

}