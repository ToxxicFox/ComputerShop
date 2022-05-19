package com.example.computershop.network

import com.example.computershop.network.data.models.responses.categories.CategoryResponse
import com.example.computershop.network.data.models.responses.products.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ShopApi {

    @GET("practice/shop/v1/categories")
    suspend fun getCategories(): CategoryResponse

    @GET("practice/shop/v1/products")
    suspend fun getProducts(@Query("page") page: Int): ProductsResponse

    @GET("practice/shop/v1/products")
    suspend fun getProductsById(@Query("filter[category_id]") categoryId: Int,
                                        @Query("page") page: Int): ProductsResponse

}