package com.example.computershop.network

import com.example.computershop.network.data.models.responses.categories.CategoryResponse
import com.example.computershop.network.data.models.responses.products.ProductsResponse
import retrofit2.http.GET

interface ShopApi {

    @GET("practice/shop/v1/categories")
    suspend fun getCategories(): CategoryResponse

    @GET("practice/shop/v1/products")
    suspend fun getProducts(): ProductsResponse

}