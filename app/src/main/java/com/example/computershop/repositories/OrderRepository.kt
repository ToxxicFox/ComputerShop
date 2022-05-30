package com.example.computershop.repositories

import com.example.computershop.network.ShopApi
import com.example.computershop.network.data.models.requests.CreateOrderRequestModel

class OrderRepository(
    private val api: ShopApi
) : BaseRepository() {

    suspend fun createOrder(
        token: String,
        order: CreateOrderRequestModel
    ) = safeApiCall {
        api.createOrder(token, order)
    }

}