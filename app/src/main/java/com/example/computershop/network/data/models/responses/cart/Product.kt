package com.example.computershop.network.data.models.responses.cart

data class Product(
    val category: Category,
    val discount: Int,
    val id: Int,
    val img: String,
    val info: String,
    val link: String,
    val price: Int,
    val price_with_discount: Int,
    val title: String,
    val vendor: Vendor
)