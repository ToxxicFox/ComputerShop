package com.example.computershop.ui.cart

import androidx.lifecycle.*
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.responses.cart.CartResponse
import com.example.computershop.repositories.CartRepository
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: CartRepository
) : ViewModel() {

    private val cartListLiveData: MutableLiveData<ResultValue<CartResponse>> =
        MutableLiveData()
    val cartList: LiveData<ResultValue<CartResponse>>
        get() = cartListLiveData

    fun getCart(token: String) = viewModelScope.launch {
        cartListLiveData.value = repository.getBasket("Bearer $token")
    }

}