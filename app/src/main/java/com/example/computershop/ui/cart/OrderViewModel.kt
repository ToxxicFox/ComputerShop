package com.example.computershop.ui.cart

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.requests.CreateOrderRequestModel
import com.example.computershop.network.data.models.responses.order.CreateOrderResponseModel
import com.example.computershop.repositories.OrderRepository
import kotlinx.coroutines.launch

class OrderViewModel(
    private val repository: OrderRepository
) : ViewModel() {

    var token: String? = null
    val bundle = Bundle()

    private var orderResponseLiveData: MutableLiveData<ResultValue<CreateOrderResponseModel>> =
        MutableLiveData()
    val cartList: MutableLiveData<ResultValue<CreateOrderResponseModel>>
        get() = orderResponseLiveData

    fun getToken(authToken: String) {
        this.token = authToken
    }

    fun createOrder(request: CreateOrderRequestModel) = viewModelScope.launch {
        cartList.value = repository.createOrder("Bearer $token", request)
    }

    fun getOrderId(id: Int, message: String) {
        val orderId = id.toString()
        bundle.putString("orderId", orderId)
        bundle.putString("orderName", message)
        bundle.putString("token", token)
    }

}