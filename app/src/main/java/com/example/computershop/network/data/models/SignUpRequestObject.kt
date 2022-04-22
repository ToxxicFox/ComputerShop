package com.example.computershop.network.data.models

data class SignUpRequestObject(
    val email: String,
    val password: String,
    val password_confirmation: String
)