package com.example.computershop.repositories

import com.example.computershop.network.ShopApi
import com.example.computershop.network.data.models.requests.CartAddRequest

class CartRepository(
    private val api: ShopApi
) : BaseRepository() {

    suspend fun getBasket(
        token: String
    ) = safeApiCall {
        api.getBasket(token)
    }

    suspend fun getItemFromBasket(
        token: String,
        basketItemId: Int
    ) = safeApiCall {
        api.getItemFromBasket(token, basketItemId)
    }

    suspend fun addItemToBasket(
        token: String,
        item: CartAddRequest
    ) = safeApiCall {
        api.addItemToBasket(token, item)
    }

    suspend fun changeQuantityItemInBasket(
        token: String,
        basketItemId: Int,
        quantity: Int
    ) = safeApiCall {
        api.changeQuantityItemInBasket(token, basketItemId, quantity)
    }

    suspend fun deleteItemFromBasket(
        token: String,
        basketItemId: Int
    ) = safeApiCall {
        api.deleteItemFromBasket(token, basketItemId)
    }

}