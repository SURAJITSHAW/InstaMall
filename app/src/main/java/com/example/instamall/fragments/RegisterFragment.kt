package com.example.instamall.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.instamall.R
import com.example.instamall.activities.ShoppingActivity
import com.example.instamall.databinding.FragmentRegisterBinding
import com.example.instamall.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _bindig: FragmentRegisterBinding? = null
    private val binding get() = _bindig!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindig = FragmentRegisterBinding.inflate(inflater, container, false)
        // Set up navigation to RegisterFragment when "Register" link is clicked
        binding.loginLink.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {

            binding.progressBar.visibility = View.VISIBLE
            binding.btnRegister.visibility = View.GONE

            val fullName = binding.etFullname.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                binding.btnRegister.visibility = View.VISIBLE
            } else {
                viewModel.registerUser(fullName, email, password)
            }
        }

        viewModel.registerState.observe(viewLifecycleOwner) { result ->
            val (success, message) = result
            if (success) {
                Toast.makeText(context, "Registration Successful. Logging in...", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), ShoppingActivity::class.java))
                requireActivity().finish()
                binding.progressBar.visibility = View.GONE
                binding.btnRegister.visibility = View.VISIBLE
            } else {
                Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                binding.btnRegister.visibility = View.VISIBLE
            }
        }
    }
}