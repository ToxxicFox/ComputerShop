package com.example.computershop.ui.cart

import androidx.lifecycle.*
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.requests.CartUpdateRequest
import com.example.computershop.network.data.models.responses.cart.CartResponse
import com.example.computershop.network.data.models.responses.cart.CartResponseAfterChanges
import com.example.computershop.repositories.CartRepository
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: CartRepository
) : ViewModel() {

    var token: String? = null
    private var updateQuantityRequest: CartUpdateRequest? = null

    private var cartListLiveData: MutableLiveData<ResultValue<CartResponse>> =
        MutableLiveData()
    val cartList: MutableLiveData<ResultValue<CartResponse>>
        get() = cartListLiveData

    private var cartListLiveDataAfterChanges: MutableLiveData<ResultValue<CartResponseAfterChanges>> =
        MutableLiveData()
    val cartListAfterChanges: MutableLiveData<ResultValue<CartResponseAfterChanges>>
        get() = cartListLiveDataAfterChanges

    fun getCart(token: String) = viewModelScope.launch {
        cartListLiveData.value = repository.getBasket("Bearer $token")
    }

    fun getToken(authToken: String) {
        this.token = authToken
    }

    fun increaseQuantity(itemQuantity: Int) {
        var incQuantity = itemQuantity
        incQuantity++
        updateQuantityRequest = CartUpdateRequest(incQuantity)
    }

    fun reduceQuantity(basketItemId: Int, itemQuantity: Int) {
        var decQuantity = itemQuantity
        decQuantity--
        if (decQuantity == 0) {
            deleteItemFromCart(basketItemId)
        } else {
            updateQuantityRequest = CartUpdateRequest(decQuantity)
        }
    }

    fun deleteItemFromCart(basketItemId: Int) =
        viewModelScope.launch {
            cartListLiveDataAfterChanges.value =
                repository.deleteItemFromBasket("Bearer $token", basketItemId)
            cartListAfterChanges.value = null
            getCart("Bearer $token")
        }

    fun changeQuantityItemInBasket(basketItemId: Int) =
        viewModelScope.launch {
            cartListLiveDataAfterChanges.value =
                updateQuantityRequest?.let {
                repository.changeQuantityItemInBasket("Bearer $token", basketItemId, it)
            }
            cartListAfterChanges.value = null
            getCart("Bearer $token")
        }
}