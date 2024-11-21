package com.example.instamall.data


data class CartItem(
    val product: Product,
    var quantity: Int = 1
)

