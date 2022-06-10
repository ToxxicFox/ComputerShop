package com.example.computershop.network

import com.example.computershop.network.data.models.requests.LoginRequest
import com.example.computershop.network.data.models.requests.SignUpRequest
import retrofit2.http.*

interface AuthApi {

    @POST("auth/get_token")
    @Headers("Content-Type: application/json")
    suspend fun login(
        @Body user: LoginRequest
    ) : String

    @POST("practice/shop/registration")
    @Headers("Content-Type: application/json")
    suspend fun signUp(
        @Body user: SignUpRequest
    ) : String
}