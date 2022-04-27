package com.example.computershop.repositories

import com.example.computershop.network.ShopApi

class CatalogRepository(
    private val api: ShopApi
) : BaseRepository(){

    suspend fun getCategories() = safeApiCall {
        api.getCategories()
    }

    suspend fun getProducts() = safeApiCall {
        api.getProducts()
    }

}