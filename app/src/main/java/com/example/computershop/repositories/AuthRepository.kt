package com.example.computershop.repositories

import com.example.computershop.network.AuthApi
import com.example.computershop.network.data.LoginRequestObject

class AuthRepository(
    private val api: AuthApi
) : BaseRepository() {

    suspend fun login(
        user: LoginRequestObject
    ) = safeApiCall {
        api.login(user)
    }

}