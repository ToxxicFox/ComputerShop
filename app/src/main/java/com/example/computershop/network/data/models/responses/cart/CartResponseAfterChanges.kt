package com.example.computershop.network.data.models.responses.cart

data class CartResponseAfterChanges(
    val basket: Basket,
    val message: String,
    val success: Boolean
)