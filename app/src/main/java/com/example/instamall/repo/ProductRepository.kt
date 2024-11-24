package com.example.instamall.repo

import com.example.instamall.data.Product
import com.google.firebase.firestore.FirebaseFirestore


class ProductRepository {
    private val firestore = FirebaseFirestore.getInstance()

    fun getSpecialProducts(onSuccess: (List<Product>) -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("products")
            .whereEqualTo("category", "special")
            .get()
            .addOnSuccessListener { snapshot ->
                val products = snapshot.documents.mapNotNull { it.toObject(Product::class.java) }
                onSuccess(products)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun getBestDealsProducts(onSuccess: (List<Product>) -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("products")
            .whereEqualTo("category", "special")
            .get()
            .addOnSuccessListener { snapshot ->
                val products = snapshot.documents.mapNotNull { it.toObject(Product::class.java) }
                onSuccess(products)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun getBestProducts(onSuccess: (List<Product>) -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("products")
            .whereEqualTo("category", "all")
            .get()
            .addOnSuccessListener { snapshot ->
                val products = snapshot.documents.mapNotNull { it.toObject(Product::class.java) }
                onSuccess(products)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}