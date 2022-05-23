package com.example.computershop.network.data.models.responses.cart

data class CartData(
    val id: Int,
    val product: Product,
    val quantity: Int
)