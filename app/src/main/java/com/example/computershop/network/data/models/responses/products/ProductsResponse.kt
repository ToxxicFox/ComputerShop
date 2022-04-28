package com.example.computershop.network.data.models.responses.products

data class ProductsResponse(
    val data: ArrayList<ProductData>,
    val links: Links,
    val meta: Meta
)