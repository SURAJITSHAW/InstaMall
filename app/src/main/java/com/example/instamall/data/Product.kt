package com.example.instamall.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String? = null, // Nullable for products not yet saved
    val name: String = "",
    val category: String = "",
    val price: Float = 0.0f, // Default value for price
    val offerPercentage: Float? = null, // Optional discount
    val description: String? = null, // Optional product description
    val colors: List<String>? = null, // Optional list of hex codes like "#FF5733"
    val sizes: List<String>? = null, // Optional sizes (e.g., "S", "M", "L")
    val imageUrls: List<String> = emptyList() // Default to empty list
) : Parcelable
