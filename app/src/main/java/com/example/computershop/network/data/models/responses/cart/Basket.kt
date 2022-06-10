package com.example.computershop.network.data.models.responses.cart

data class Basket(
    val data: ArrayList<CartData>,
    val meta: Meta
)