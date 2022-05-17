package com.example.computershop.ui.profile

import androidx.lifecycle.*
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.requests.LoginRequest
import com.example.computershop.network.data.models.requests.SignUpRequest
import com.example.computershop.repositories.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val assignedToken: MutableLiveData<ResultValue<String>> = MutableLiveData()
    val token: LiveData<ResultValue<String>>
        get() = assignedToken

    fun login(
        user: LoginRequest
    ) = viewModelScope.launch {
        assignedToken.value = repository.login(user)
    }

    fun signUp(
        user: SignUpRequest
    ) = viewModelScope.launch {
        assignedToken.value = repository.signUp(user)
    }

    fun saveAuthToken(token: String) = viewModelScope.launch {
        repository.saveAuthToken(token)
    }

}