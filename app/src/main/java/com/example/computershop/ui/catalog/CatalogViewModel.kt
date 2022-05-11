package com.example.computershop.ui.catalog

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.responses.categories.CategoryResponse
import com.example.computershop.repositories.CatalogRepository
import kotlinx.coroutines.launch


class CatalogViewModel(
    private val repository: CatalogRepository
) : ViewModel() {

    private val categoryListLiveData: MutableLiveData<ResultValue<CategoryResponse>> = MutableLiveData()
    val categoryList: LiveData<ResultValue<CategoryResponse>>
        get() = categoryListLiveData

    val products = Pager(config = PagingConfig(pageSize = 12, maxSize = 45), pagingSourceFactory = {
        ProductPagingSource(repository)
    }).flow.cachedIn(viewModelScope)

    fun getCategories() = viewModelScope.launch {
        categoryListLiveData.value = repository.getCategories()
    }

}