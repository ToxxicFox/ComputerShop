package com.example.computershop.ui.cart

import androidx.lifecycle.*
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.responses.cart.CartResponse
import com.example.computershop.repositories.CartRepository
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: CartRepository
) : ViewModel() {

    private var token: String? = null

    private var cartListLiveData: MutableLiveData<ResultValue<CartResponse>> =
        MutableLiveData()
    val cartList: MutableLiveData<ResultValue<CartResponse>>
        get() = cartListLiveData

    fun getCart(token: String) = viewModelScope.launch {
        cartListLiveData.value = repository.getBasket("Bearer $token")
    }

    fun getToken(authToken: String) {
        this.token = authToken
    }

    fun deleteItemFromCart(basketId: Int) =
        viewModelScope.launch {
            repository.deleteItemFromBasket("Bearer $token", basketId)
            getCart("Bearer $token")
        }
}