package com.example.instamall.data


data class CartItem(
    val user: User,
    val product: Product,
    var quantity: Int = 1
)

