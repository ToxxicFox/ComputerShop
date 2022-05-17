package com.example.computershop.network.data.models.requests

data class SignUpRequest(
    val email: String,
    val password: String,
    val password_confirmation: String
)