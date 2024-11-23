package com.example.instamall.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instamall.R
import com.example.instamall.adapters.CartAdapter
import com.example.instamall.data.CartItem
import com.example.instamall.data.Product
import com.example.instamall.databinding.FragmentCartBinding
import com.example.instamall.viewmodel.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.razorpay.Checkout
import org.json.JSONObject
import java.util.Calendar

class CartFragment : Fragment(R.layout.fragment_cart){

    private val sharedViewModel: SharedViewModel by activityViewModels()


    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartAdapter: CartAdapter
    private val cartItems = mutableListOf<CartItem>()

    private val firestore = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)

        // Observing payment success LiveData
        sharedViewModel.paymentSuccess.observe(viewLifecycleOwner, Observer { paymentId ->
            Log.d("CartFragment", "Payment success observer triggered: $paymentId")
            paymentId?.let {
                saveOrderDetails(it)  // Process the payment success
                Toast.makeText(requireContext(), "Payment successful: $it", Toast.LENGTH_SHORT).show()
                Log.d("CartFragment", "Payment successful with ID: $it")
            }

            // Reset the payment success to null to avoid repeating the observer action
            sharedViewModel.onPaymentSuccess(null)
        })

        setupRecyclerView()
        fetchCartItems()

        binding.checkoutButton.setOnClickListener {
            if (cartItems.isEmpty()) {
                Toast.makeText(requireContext(), "Your cart is empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val totalAmount = cartItems.sumOf { it.product.price.toDouble() * it.quantity.toDouble() }
            initiatePayment(totalAmount)
        }

    }
    private fun initiatePayment(amount: Double) {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_D5O8jlIA49q9ZX") // Replace with your Razorpay key ID

        try {
            val options = JSONObject()
            options.put("name", "InstaMall")
            options.put("description", "Order Payment")
            options.put("currency", "INR")
            options.put("amount", (amount * 100).toInt()) // Amount in paise
            options.put("prefill.email", currentUser?.email ?: "guest@example.com")
            options.put("prefill.contact", "9876543210") // Replace with user's phone number

            checkout.open(requireActivity(), options)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Payment initialization failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }



    fun saveOrderDetails(paymentId: String?) {

        currentUser?.let { user ->
            // Log and toast to track current user information
            Log.d("OrderDebug", "Current User ID: ${user.uid}")
            Toast.makeText(requireContext(), "Current User ID: ${user.uid}", Toast.LENGTH_SHORT).show()

            // Generate a unique order ID
            val orderId = firestore.collection("orders").document().id
            Log.d("OrderDebug", "Generated Order ID: $orderId")
            Toast.makeText(requireContext(), "Generated Order ID: $orderId", Toast.LENGTH_SHORT).show()

            // Prepare order details
            val orderDetails = hashMapOf(
                "orderId" to orderId,
                "userId" to user.uid,
                "cartItems" to cartItems.map {
                    hashMapOf(
                        "productId" to it.product.id,
                        "name" to it.product.name,
                        "price" to it.product.price,
                        "quantity" to it.quantity
                    )
                },
                "totalAmount" to cartItems.sumOf { it.product.price.toDouble() * it.quantity.toDouble() },
                "orderStatus" to "Paid",
                "paymentId" to paymentId,
                "orderDate" to System.currentTimeMillis(),
                "expectedDeliveryDate" to calculateExpectedDeliveryDate()
            )

            // Log the full order details
            Log.d("OrderDebug", "Order Details: $orderDetails")
            Toast.makeText(requireContext(), "Preparing order details...", Toast.LENGTH_SHORT).show()

            // Save order details to Firestore
            firestore.collection("orders").document(orderId)
                .set(orderDetails)
                .addOnSuccessListener {
                    Log.d("OrderDebug", "Order saved successfully: $orderId")
                    Toast.makeText(requireContext(), "Order placed successfully!", Toast.LENGTH_SHORT).show()
                    clearCart() // Clear the cart after successful order placement
                }
                .addOnFailureListener { e ->
                    Log.e("OrderDebug", "Failed to save order: ${e.message}")
                    Toast.makeText(requireContext(), "Failed to save order: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } ?: run {
            // Log and toast if currentUser is null
            Log.e("OrderDebug", "No authenticated user found!")
            Toast.makeText(requireContext(), "Error: No authenticated user found!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearCart() {
        currentUser?.let { user ->
            val cartRef = firestore.collection("users").document(user.uid).collection("cart")
            cartRef.get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot) {
                        document.reference.delete()
                    }
                    cartItems.clear()
                    cartAdapter.notifyDataSetChanged()
                    calculateTotalAmount()
                }
        }
    }
    private fun calculateExpectedDeliveryDate(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, 5) // Assuming 5 days delivery time
        return calendar.timeInMillis
    }




    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            cartItems,
            onQuantityChanged = { position, newQty ->
                cartItems[position].quantity = newQty
                cartAdapter.notifyItemChanged(position)
                updateCartItemQuantity(cartItems[position].product.id!!, newQty)
                calculateTotalAmount() // Recalculate total after quantity change
            },
            onRemoveItem = { position ->
                val removedItem = cartItems[position]
                removeCartItem(removedItem.product.id!!)
                cartItems.removeAt(position)
                cartAdapter.notifyItemRemoved(position)
                Toast.makeText(requireContext(), "Item removed", Toast.LENGTH_SHORT).show()
                calculateTotalAmount() // Recalculate total after item removal
            }
        )

        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    private fun fetchCartItems() {
        currentUser?.let { user ->
            val cartRef = firestore.collection("users")
                .document(user.uid)
                .collection("cart")

            cartRef.get()
                .addOnSuccessListener { querySnapshot ->
                    cartItems.clear()
                    for (document in querySnapshot) {
                        val product = document.get("product") as Map<String, Any>
                        val quantity = document.getLong("quantity")?.toInt() ?: 1

                        val cartItem = CartItem(
                            Product(
                                id = document.id,
                                name = product["name"].toString(),
                                category = product["category"].toString(),
                                price = (product["price"] as Number).toFloat(),
                                description = product["description"]?.toString(),
                                imageUrls = product["imageUrls"] as List<String>
                            ),
                            quantity
                        )
                        cartItems.add(cartItem)
                    }
                    cartAdapter.notifyDataSetChanged()
                    calculateTotalAmount() // Calculate total after fetching items
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Failed to load cart items: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } ?: Toast.makeText(requireContext(), "Please log in to view the cart", Toast.LENGTH_SHORT).show()
    }

    private fun calculateTotalAmount() {
        val total = cartItems.sumOf { it.product.price.toDouble() * it.quantity.toDouble() }
        binding.totalAmount.text = "₹%.2f".format(total) // Update the total amount TextView
    }


    private fun updateCartItemQuantity(productId: String, quantity: Int) {
        currentUser?.let { user ->
            val cartRef = firestore.collection("users")
                .document(user.uid)
                .collection("cart")
                .document(productId)

            cartRef.update("quantity", quantity)
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Failed to update quantity: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun removeCartItem(productId: String) {
        currentUser?.let { user ->
            val cartRef = firestore.collection("users")
                .document(user.uid)
                .collection("cart")
                .document(productId)

            cartRef.delete()
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Failed to remove item: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}
