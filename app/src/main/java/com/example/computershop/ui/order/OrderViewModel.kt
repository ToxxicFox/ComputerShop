package com.example.computershop.ui.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.requests.CreateOrderRequestModel
import com.example.computershop.network.data.models.responses.order.OrderResponseModel
import com.example.computershop.repositories.OrderRepository
import kotlinx.coroutines.launch

class OrderViewModel(
    private val repository: OrderRepository
) : ViewModel() {

    var token = "295|8FRNms4XQqhUhcCaCVIqcdCA0OzbzUXXCJn3LM0X"

    private var orderResponseLiveData: MutableLiveData<ResultValue<OrderResponseModel>> =
        MutableLiveData()
    val cartList: MutableLiveData<ResultValue<OrderResponseModel>>
        get() = orderResponseLiveData

    fun createOrder(request: CreateOrderRequestModel) = viewModelScope.launch {
        cartList.value = repository.createOrder("Bearer $token", request)
    }

}