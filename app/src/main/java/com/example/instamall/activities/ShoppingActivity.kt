package com.example.instamall.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.instamall.R
import com.example.instamall.databinding.ActivityShoppingBinding
import com.example.instamall.fragments.CartFragment
import com.example.instamall.viewmodel.SharedViewModel
import com.razorpay.PaymentResultListener

class ShoppingActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var binding: ActivityShoppingBinding
    private  val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle system insets (padding for status/navigation bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply padding for the main layout but not for BottomNavigationView
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0) // No bottom padding here
            insets
        }

        val navHostFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        val navController = navHostFragment.navController

        // Setup BottomNavigationView with Navigation Controller
        binding.bottomNavView.setupWithNavController(navController)


    }


    override fun onPaymentSuccess(paymentId: String?) {
        Log.d("ShoppingActivity", "Payment success started: $paymentId")
        paymentId?.let {
            // Update the ViewModel with payment success
            sharedViewModel.onPaymentSuccess(it)
        }
        Log.d("ShoppingActivity", "Payment success done: $paymentId")
    }


    // Handle Razorpay error
    override fun onPaymentError(code: Int, response: String?) {
        Toast.makeText(this, "Payment failed: $response", Toast.LENGTH_SHORT).show()
    }
}
