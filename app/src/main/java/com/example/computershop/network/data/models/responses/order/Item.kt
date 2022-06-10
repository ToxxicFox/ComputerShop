package com.example.computershop.network.data.models.responses.order

data class Item(
    val id: Int,
    val product: Product,
    val quantity: Int
)