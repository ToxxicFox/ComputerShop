package com.example.computershop.ui.catalog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.responses.categories.CategoryResponse
import com.example.computershop.network.data.models.responses.products.ProductsResponse
import com.example.computershop.repositories.CatalogRepository
import kotlinx.coroutines.launch


private const val TAG = "CATEGORY_VM"

class CatalogViewModel(
    private val repository: CatalogRepository
) : ViewModel() {

    private val categoryListLiveData: MutableLiveData<ResultValue<CategoryResponse>> = MutableLiveData()
    val categoryList: LiveData<ResultValue<CategoryResponse>>
        get() = categoryListLiveData

    private val productListLiveData: MutableLiveData<ResultValue<ProductsResponse>> = MutableLiveData()
    val productList: LiveData<ResultValue<ProductsResponse>>
        get() = productListLiveData


    fun getCategories() = viewModelScope.launch {
        categoryListLiveData.value = repository.getCategories()
    }

    fun getProducts() = viewModelScope.launch {
        productListLiveData.value = repository.getProducts()
        val response = productList.value
        Log.d(TAG, response.toString())
    }

}