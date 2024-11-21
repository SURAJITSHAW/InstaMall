package com.example.instamall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instamall.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _registerState = MutableLiveData<Pair<Boolean, String?>>()
    val registerState: LiveData<Pair<Boolean, String?>> = _registerState

    fun registerUser(fullName: String, email: String, password: String) {
        repository.registerUser(fullName, email, password) { success, message ->
            _registerState.postValue(Pair(success, message))
        }
    }
}
