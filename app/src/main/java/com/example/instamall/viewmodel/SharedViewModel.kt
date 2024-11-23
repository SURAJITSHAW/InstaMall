package com.example.instamall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _paymentSuccess = MutableLiveData<String?>()
    val paymentSuccess: LiveData<String?> get() = _paymentSuccess

    fun onPaymentSuccess(paymentId: String?) {
        _paymentSuccess.value = paymentId
    }
}
