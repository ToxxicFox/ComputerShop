package com.example.computershop.ui.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.responses.categories.CategoryResponse
import com.example.computershop.network.data.models.responses.products.ProductData
import com.example.computershop.repositories.CatalogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.FieldPosition


class CatalogViewModel(
    private val repository: CatalogRepository
) : ViewModel() {

    private val categoryListLiveData: MutableLiveData<ResultValue<CategoryResponse>> = MutableLiveData()
    val categoryList: LiveData<ResultValue<CategoryResponse>>
        get() = categoryListLiveData

    fun getCategories() = viewModelScope.launch {
        categoryListLiveData.value = repository.getCategories()
    }

    fun getProducts(): Flow<PagingData<ProductData>> {

        return Pager(config = PagingConfig(pageSize = 12, maxSize = 85),
        pagingSourceFactory = {
            ProductPagingSource(repository)
        }).flow.cachedIn(viewModelScope)

    }

}