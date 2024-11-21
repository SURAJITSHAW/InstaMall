package com.example.instamall.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.instamall.R
import com.example.instamall.activities.ShoppingActivity
import com.example.instamall.databinding.FragmentLoginBinding
import com.example.instamall.utils.Resource
import com.example.instamall.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize view binding
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set up navigation to RegisterFragment when "Register" link is clicked
        binding.registerLink.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginLiveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    // Show loading state
                    showLoadingIndicator(true)
                }

                is Resource.Success -> {
                    // Handle success (navigate to another activity, show a message)
                    val user = result.data
                    Toast.makeText(requireContext(), "Login Successful!", Toast.LENGTH_SHORT).show()
                    navigateToHomeScreen()
                }

                is Resource.Failure -> {
                    // Handle error
                    Toast.makeText(requireContext(), "Error: ${result.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        // Trigger login when the button is clicked
        binding.apply {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                viewModel.login(email, password)

            }
        }
    }

    private fun showLoadingIndicator(isLoading: Boolean) {
        if (isLoading) {
            binding.progressbar.visibility = View.VISIBLE
            binding.btnLogin.visibility = View.GONE
        } else {

            binding.progressbar.visibility = View.GONE
            binding.btnLogin.visibility = View.VISIBLE
        }
    }

    private fun navigateToHomeScreen() {
        // Navigate to the home screen or main activity after successful login
        startActivity(Intent(requireContext(), ShoppingActivity::class.java))
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leaks
    }
}
