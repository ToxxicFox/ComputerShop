package com.example.computershop.ui.catalog

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.responses.products.ProductData
import com.example.computershop.repositories.CatalogRepository
import java.lang.Exception

class ProductPagingSource (private val repository: CatalogRepository): PagingSource<Int, ProductData>() {

    override fun getRefreshKey(state: PagingState<Int, ProductData>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductData> {
        val nextPage = params.key ?: FIRST_PAGE_INDEX
        val response = repository.getProducts(nextPage)
        var nextPageNumber: Int? = null
        lateinit var data: ArrayList<ProductData>

        return try {
            if (response is ResultValue.Success){
                val uri = Uri.parse(response.value.links.next)
                val nextPageParameter = uri.getQueryParameter("page")
                nextPageNumber = nextPageParameter?.toInt()
                data = response.value.data
            }
            LoadResult.Page(data = data, prevKey = null, nextKey = nextPageNumber)
        }
        catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }

}