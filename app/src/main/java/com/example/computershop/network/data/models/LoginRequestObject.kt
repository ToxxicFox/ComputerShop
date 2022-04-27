package com.example.computershop.network.data.models

import retrofit2.http.Field

data class LoginRequestObject(
    val email: String,
    val password: String,
    val device_name: String
)