package com.example.instamall.utils

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun FragmentActivity?.toggleBottomNav(isVisible: Boolean, bottomNavId: Int) {
    this?.findViewById<BottomNavigationView>(bottomNavId)?.visibility =
        if (isVisible) View.VISIBLE else View.GONE
}
