package com.example.computershop.network.data.models.responses.cart

data class CartResponse(
    val data: ArrayList<CartData>,
    val meta: Meta
)