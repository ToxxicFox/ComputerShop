package com.example.computershop.ui.catalog

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.responses.products.ProductData
import com.example.computershop.network.data.models.responses.products.ProductsResponse
import com.example.computershop.repositories.CatalogRepository
import java.lang.Exception

class ProductPagingSource (private val repository: CatalogRepository,
                           private val categoryItemId: Int?): PagingSource<Int, ProductData>() {

    override fun getRefreshKey(state: PagingState<Int, ProductData>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductData> {
        val nextPage = params.key ?: FIRST_PAGE_INDEX
        val response: ResultValue<ProductsResponse> =
            categoryItemId?.let { repository.getProductsByCategories(it, nextPage) }
                ?: repository.getProducts(nextPage)
        var lastPageNumber: Int? = null
        lateinit var data: ArrayList<ProductData>

        return try {
            if (response is ResultValue.Success){
                val uri = Uri.parse(response.value.links.last)
                val lastPageParameter = uri.getQueryParameter("page")
                lastPageNumber = lastPageParameter?.toInt()
                data = response.value.data
            }
            LoadResult.Page(data = data,
                prevKey = if (nextPage == 1) null else nextPage-1,
                nextKey = if (nextPage == lastPageNumber) null else nextPage+1)
        }
        catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }

}