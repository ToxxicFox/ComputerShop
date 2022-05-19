package com.example.computershop.ui.catalog

import androidx.lifecycle.*
import androidx.paging.*
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.responses.categories.CategoryResponse
import com.example.computershop.network.data.models.responses.products.ProductData
import com.example.computershop.repositories.CatalogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class CatalogViewModel(
    private val repository: CatalogRepository
) : ViewModel() {

    private var categoryId: Int? = null

    private val categoryListLiveData: MutableLiveData<ResultValue<CategoryResponse>> =
        MutableLiveData()
    val categoryList: LiveData<ResultValue<CategoryResponse>>
        get() = categoryListLiveData

    var products: Flow<PagingData<ProductData>>? = Pager(config = PagingConfig(pageSize = 12, maxSize = 85),
        pagingSourceFactory = {
            ProductPagingSource(repository, setCategoryId()) }).flow.cachedIn(viewModelScope)

    private fun getCategories() = viewModelScope.launch {
        categoryListLiveData.value = repository.getCategories()
    }

    fun getCategoryId(id: Int?) {
        categoryId = if (categoryId == id) {
            null
        } else {
            id
        }
    }

    private fun setCategoryId(): Int? {
        return this.categoryId
    }

    private fun isCategoryIdNull(): Boolean {
        return categoryId == null
    }

    fun getProductsByCategory() {
        if (isCategoryIdNull()) {
            products = null
        }
        products =  Pager(config = PagingConfig(pageSize = 12, maxSize = 85),
            pagingSourceFactory = {
                ProductPagingSource(repository, setCategoryId())
            }).flow.cachedIn(viewModelScope)
    }

    init {
        getCategories()
    }

}