package com.example.instamall.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.instamall.R
import com.example.instamall.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _bindig: FragmentRegisterBinding? = null
    private val binding get() = _bindig!!

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
}