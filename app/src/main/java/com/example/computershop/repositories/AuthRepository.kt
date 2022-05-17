package com.example.computershop.repositories

import com.example.computershop.network.AuthApi
import com.example.computershop.network.data.models.requests.LoginRequest
import com.example.computershop.network.data.models.requests.SignUpRequest

class AuthRepository(
    private val api: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository() {

    suspend fun login(
        user: LoginRequest
    ) = safeApiCall {
        api.login(user)
    }

    suspend fun signUp(
        user: SignUpRequest
    ) = safeApiCall {
        api.signUp(user)
    }

    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }
}