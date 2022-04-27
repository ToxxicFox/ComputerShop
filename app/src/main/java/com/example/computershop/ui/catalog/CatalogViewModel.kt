package com.example.computershop.ui.catalog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.responses.categories.CategoryResponse
import com.example.computershop.repositories.CatalogRepository
import kotlinx.coroutines.launch


private const val TAG = "CATEGORY_VM"

class CatalogViewModel(
    private val repository: CatalogRepository
) : ViewModel() {

    private val categoryListLiveData: MutableLiveData<ResultValue<CategoryResponse>> = MutableLiveData()
    val categoryList: LiveData<ResultValue<CategoryResponse>>
        get() = categoryListLiveData


    fun getCategories() = viewModelScope.launch {
        categoryListLiveData.value = repository.getCategories()
        Log.d(TAG, categoryList.toString())
    }

}