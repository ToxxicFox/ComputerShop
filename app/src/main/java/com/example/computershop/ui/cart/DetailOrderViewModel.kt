package com.example.computershop.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.responses.order.GetOrderByIdResponseModel
import com.example.computershop.repositories.OrderRepository
import kotlinx.coroutines.launch

class DetailOrderViewModel(
    private val repository: OrderRepository
) : ViewModel() {

    private var orderId: Int = 0
    var token: String? = null
    var orderNumber: String? = null

    private var orderListLiveData: MutableLiveData<ResultValue<GetOrderByIdResponseModel>> =
        MutableLiveData()
    val orderProductList: MutableLiveData<ResultValue<GetOrderByIdResponseModel>>
        get() = orderListLiveData

    fun getOrderDetails() = viewModelScope.launch {
        orderProductList.value = repository.getOrdersById("Bearer $token", orderId)
    }

    fun getOrderId(orderIdStr: String) {
        this.orderId = orderIdStr.toInt()
    }

    fun getToken(authToken: String) {
        this.token = authToken
    }

    fun getOrderName(orderName: String){
        this.orderNumber = orderName.substringBefore(" создан")
    }

}