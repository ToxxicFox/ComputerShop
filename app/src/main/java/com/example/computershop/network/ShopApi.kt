package com.example.computershop.network

import com.example.computershop.network.data.models.requests.CartAddRequest
import com.example.computershop.network.data.models.responses.cart.CartResponse
import com.example.computershop.network.data.models.responses.cart.CartResponseAfterChanges
import com.example.computershop.network.data.models.responses.cart.CartData
import com.example.computershop.network.data.models.responses.categories.CategoryResponse
import com.example.computershop.network.data.models.responses.products.ProductsResponse
import retrofit2.http.*

interface ShopApi {

    @GET("practice/shop/v1/categories")
    suspend fun getCategories(): CategoryResponse

    @GET("practice/shop/v1/products")
    suspend fun getProducts(@Query("page") page: Int): ProductsResponse

    @GET("practice/shop/v1/products")
    suspend fun getProductsByCategories(@Query("filter[category_id]") categoryId: Int,
                                        @Query("page") page: Int): ProductsResponse

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("practice/shop/v1/basket")
    suspend fun getBasket(@Header("Authorization") token: String): CartResponse

    @Headers("Accept: application/json","Content-Type: application/json")
    @GET("practice/shop/v1/basket")
    suspend fun getItemFromBasket(@Header("Authorization") token: String,
                                    @Path("id") basketId: Int): CartData

    @Headers("Accept: application/json","Content-Type: application/json")
    @POST("practice/shop/v1/basket")
    suspend fun addItemToBasket(@Header("Authorization") token: String,
                                @Body item: CartAddRequest): CartResponseAfterChanges

    @Headers("Accept: application/json","Content-Type: application/json")
    @PUT("practice/shop/v1/basket/{id}")
    suspend fun changeQuantityItemInBasket(@Header("Authorization") token: String,
                                           @Path("id") basketId: Int,
                                           @Body quantity: Int): CartResponseAfterChanges

    @Headers("Accept: application/json","Content-Type: application/json")
    @DELETE("practice/shop/v1/basket/{id}")
    suspend fun deleteItemFromBasket(@Header("Authorization") token: String,
                                     @Path("id") basketId: Int): CartResponseAfterChanges

}