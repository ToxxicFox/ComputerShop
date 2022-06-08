package com.example.computershop.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.responses.order.GetAllOrdersResponseModel
import com.example.computershop.repositories.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {

    var token: String? = null

    private var profileOrderListMutableLiveData: MutableLiveData<ResultValue<GetAllOrdersResponseModel>> =
        MutableLiveData()
    val orderProductListLiveData: MutableLiveData<ResultValue<GetAllOrdersResponseModel>>
        get() = profileOrderListMutableLiveData

    fun getOrders() = viewModelScope.launch {
        profileOrderListMutableLiveData.value = repository.getOrders("Bearer $token")
    }

    fun getToken(authToken: String) {
        this.token = authToken
    }

}