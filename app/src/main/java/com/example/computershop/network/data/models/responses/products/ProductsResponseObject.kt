package com.example.computershop.network.data.models.responses.products

data class ProductsResponseObject(
    val `data`: List<Data>,
    val links: Links,
    val meta: Meta
)