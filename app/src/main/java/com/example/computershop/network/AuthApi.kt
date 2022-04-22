package com.example.computershop.network

import com.example.computershop.network.data.LoginRequestObject
import retrofit2.http.*

interface AuthApi {

    @POST("auth/get_token")
    @Headers("Content-Type: application/json")
    suspend fun login(
        @Body user: LoginRequestObject
    ) : String
}