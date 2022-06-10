package com.example.computershop.network.data.models.requests

import retrofit2.http.Field

data class LoginRequest(
    val email: String,
    val password: String,
    val device_name: String
)