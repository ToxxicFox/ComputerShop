package com.example.computershop.network.data.models.responses.order

class CreateOrderResponseModel (
    val success: Boolean,
    val message: String,
    val data: List<Any?>
)