package com.example.computershop.network

import com.example.computershop.network.data.models.LoginRequestObject
import com.example.computershop.network.data.models.SignUpRequestObject
import retrofit2.http.*

interface AuthApi {

    @POST("auth/get_token")
    @Headers("Content-Type: application/json")
    suspend fun login(
        @Body user: LoginRequestObject
    ) : String

    @POST("practice/shop/registration")
    @Headers("Content-Type: application/json")
    suspend fun signUp(
        @Body user: SignUpRequestObject
    ) : String
}