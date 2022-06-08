package com.example.computershop.repositories

import com.example.computershop.network.ShopApi

class ProfileRepository(
    private val api: ShopApi
) : BaseRepository() {

    suspend fun getOrders(
        token: String
    ) = safeApiCall {
        api.getOrders(token)
    }
}