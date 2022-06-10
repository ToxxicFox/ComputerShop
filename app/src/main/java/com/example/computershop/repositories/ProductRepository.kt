package com.example.computershop.repositories

import com.example.computershop.network.ShopApi
import com.example.computershop.network.data.models.requests.CartAddRequest

class ProductRepository (
    private val api: ShopApi
): BaseRepository(){

    suspend fun addItemToBasket(
        token: String,
        item: CartAddRequest
    ) = safeApiCall {
        api.addItemToBasket(token, item)
    }

}