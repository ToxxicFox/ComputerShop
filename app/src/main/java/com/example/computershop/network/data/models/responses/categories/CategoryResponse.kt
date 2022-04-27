package com.example.computershop.network.data.models.responses.categories

data class CategoryResponse(
    val data: ArrayList<CategoryData>
)

data class CategoryData(
    val id: Int,
    val title: String,
    val info: String,
    val link: String
)