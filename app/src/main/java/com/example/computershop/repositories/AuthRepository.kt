package com.example.computershop.repositories

import com.example.computershop.network.AuthApi
import com.example.computershop.network.data.LoginRequestObject

class AuthRepository(
    private val api: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository() {

    suspend fun login(
        user: LoginRequestObject
    ) = safeApiCall {
        api.login(user)
    }

    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }

}