package com.example.computershop.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.LoginRequestObject
import com.example.computershop.repositories.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val mutableLoginResponse: MutableLiveData<ResultValue<String>> = MutableLiveData()
    val loginResponse: LiveData<ResultValue<String>>
        get() = mutableLoginResponse

    fun login(
        user: LoginRequestObject
    ) = viewModelScope.launch {
        mutableLoginResponse.value = repository.login(user)
    }

    fun saveAuthToken(token: String) = viewModelScope.launch {
        repository.saveAuthToken(token)
    }

}