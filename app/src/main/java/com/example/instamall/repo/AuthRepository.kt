package com.example.instamall.repo

import com.example.instamall.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    fun registerUser(
        fullName: String,
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    val user = hashMapOf(
                        "fullName" to fullName,
                        "email" to email,
                        "imagePath" to "" // Initial blank image path
                    )
                    userId?.let {
                        firestore.collection("users").document(it)
                            .set(user)
                            .addOnSuccessListener {
                                onResult(true, null)
                            }
                            .addOnFailureListener { e ->
                                onResult(false, e.message)
                            }
                    }
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    suspend fun login(email: String, password: String): Resource<FirebaseUser?> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user) // On success, return the user
        } catch (e: Exception) {
            Resource.Failure(e.message) // On failure, return the error message
        }
    }
}
