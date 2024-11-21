package com.example.instamall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instamall.repo.AuthRepository
import com.example.instamall.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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


    private val _loginLiveData = MutableLiveData<Resource<FirebaseUser?>>()
    val loginLiveData: LiveData<Resource<FirebaseUser?>> = _loginLiveData

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginLiveData.value = Resource.Loading() // Optional, show a loading state

            try {
                val result = repository.login(email, password)
                _loginLiveData.value = result
            } catch (e: Exception) {
                _loginLiveData.value = Resource.Failure(e.message) // Handle error if any
            }
        }
    }
}
