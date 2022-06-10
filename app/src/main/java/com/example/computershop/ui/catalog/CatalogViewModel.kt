package com.example.computershop.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
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

    var products: Flow<PagingData<ProductData>>? =
        Pager(config = PagingConfig(pageSize, maxSize),
        pagingSourceFactory = {
            ProductPagingSource(repository, setCategoryId())
        }).flow.cachedIn(viewModelScope)

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
        products =  Pager(config = PagingConfig(pageSize, maxSize),
            pagingSourceFactory = {
                ProductPagingSource(repository, setCategoryId())
            }).flow.cachedIn(viewModelScope)
    }

    init {
        getCategories()
    }

    companion object {
        const val pageSize = 12
        const val maxSize = 85
    }

}