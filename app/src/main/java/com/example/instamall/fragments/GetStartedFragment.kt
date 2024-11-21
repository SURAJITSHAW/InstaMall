package com.example.instamall.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.instamall.R
import com.example.instamall.activities.ShoppingActivity
import com.example.instamall.databinding.FragmentGetStartedBinding
import com.google.firebase.auth.FirebaseAuth

class GetStartedFragment : Fragment() {
    private var _binding: FragmentGetStartedBinding? = null
    private val binding get() = _binding!!

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGetStartedBinding.inflate(inflater, container, false)

        // Check if the user is already authenticated
        if (auth.currentUser != null) {
            startActivity(Intent(requireContext(), ShoppingActivity::class.java))
            requireActivity().finish()
        }

        // Set up navigation to RegisterFragment when "Register" link is clicked
        binding.register.setOnClickListener {
            findNavController().navigate(R.id.action_getStartedFragment_to_registerFragment)
        }
        // Set up navigation to RegisterFragment when "Register" link is clicked
        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_getStartedFragment_to_loginFragment)
        }
        return binding.root
    }

}