package com.example.computershop.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.LoginRequestObject
import com.example.computershop.network.data.TokenResponseObject
import com.example.computershop.repositories.AuthRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginResponse: MutableLiveData<ResultValue<String>> = MutableLiveData()
    val loginResponse: LiveData<ResultValue<String>>
        get() = _loginResponse

    fun login(
        user: LoginRequestObject
    ) = viewModelScope.launch {
        _loginResponse.value = repository.login(user)
    }

}