package com.example.computershop.network.data.models.requests

data class CartAddRequest(
    val product_id: Int,
    val quantity: Int
)