package com.example.instamall.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.instamall.activities.LoginRegisterActivity
import com.example.instamall.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!  // Ensuring safe access to binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        // Fetch the current user details and populate the UI
        loadUserProfile()

        handleClickListener()

        return binding.root
    }

    private fun handleClickListener() {
        binding.apply {
            logoutButton.setOnClickListener {
                // Handle logout click
                auth.signOut()
                startActivity(Intent(requireContext(), LoginRegisterActivity::class.java))
                requireActivity().finish()
            }

            changePersonDetails.setOnClickListener {
                // Handle change person details click
                Toast.makeText(requireContext(), "Change personal details", Toast.LENGTH_SHORT).show()
            }

            changePassword.setOnClickListener {
                // Handle change password click
                Toast.makeText(requireContext(), "Change password", Toast.LENGTH_SHORT).show()
            }

            yourAddresses.setOnClickListener {
                // Handle your addresses click
                Toast.makeText(requireContext(), "Your addresses", Toast.LENGTH_SHORT).show()
            }

            yourOrders.setOnClickListener {
                // Handle your orders click
                Toast.makeText(requireContext(), "Your orders", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadUserProfile() {
        // Get the current user
        val user = auth.currentUser

        if (user != null) {
            // Get user details from Firestore
            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("users").document(user.uid)

            userRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Get user data
                    val username = documentSnapshot.getString("fullName")

                    // Set the username
                    binding.usernameText.text = username


                }
            }.addOnFailureListener { exception ->
                // Handle failure (e.g., show a toast or log an error)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
