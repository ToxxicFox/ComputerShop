package com.example.computershop.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.requests.LoginRequestObject
import com.example.computershop.network.data.models.requests.SignUpRequestObject
import com.example.computershop.repositories.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val assignedToken: MutableLiveData<ResultValue<String>> = MutableLiveData()
    val token: LiveData<ResultValue<String>>
        get() = assignedToken

    fun login(
        user: LoginRequestObject
    ) = viewModelScope.launch {
        assignedToken.value = repository.login(user)
    }

    fun signUp(
        user: SignUpRequestObject
    ) = viewModelScope.launch {
        assignedToken.value = repository.signUp(user)
    }

    fun saveAuthToken(token: String) = viewModelScope.launch {
        repository.saveAuthToken(token)
    }

}