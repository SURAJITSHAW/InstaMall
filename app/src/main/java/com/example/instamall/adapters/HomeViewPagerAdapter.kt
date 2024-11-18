package com.example.instamall.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeViewPagerAdapter(
    private val catFragments: List<Fragment>,
    fm: FragmentManager,
    lc: Lifecycle
): FragmentStateAdapter(fm, lc) {
    override fun getItemCount(): Int {
        return catFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return catFragments[position]
    }

}