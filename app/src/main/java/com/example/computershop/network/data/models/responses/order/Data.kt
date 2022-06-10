package com.example.computershop.network.data.models.responses.order

data class Data(
    val address: String,
    val discount: Int,
    val fio: String,
    val id: Int,
    val items: ArrayList<Item>,
    val phone: String,
    val status: String,
    val total: Int,
    val total_with_discount: Int
)