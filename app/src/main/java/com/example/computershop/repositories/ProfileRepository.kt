package com.example.computershop.repositories

import com.example.computershop.network.ShopApi

class ProfileRepository(
    private val api: ShopApi,
    private val preferences: UserPreferences
) : BaseRepository() {

    suspend fun getOrders(
        token: String
    ) = safeApiCall {
        api.getOrders(token)
    }

    suspend fun logout() {
        preferences.clear()
    }
}