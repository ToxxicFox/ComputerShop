package com.example.computershop.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.requests.CartAddRequest
import com.example.computershop.network.data.models.responses.cart.CartResponseAfterChanges
import com.example.computershop.network.data.models.responses.products.ProductData
import com.example.computershop.repositories.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    var productRequest: CartAddRequest? = null
    private var responseAfterAdd: ResultValue<CartResponseAfterChanges>? = null
    val messageAdding = "Товар успешно добавлен в корзину"

    fun setProductRequest(request: ProductData) {
        val productId = request.id
        val quantity = DEFAULT_QUANTITY
        productRequest = CartAddRequest(productId, quantity)
    }

    suspend fun addToCart(token: String, item: CartAddRequest) = viewModelScope.launch {
        responseAfterAdd = repository.addItemToBasket("Bearer $token", item)
    }

    companion object {
        const val DEFAULT_QUANTITY = 1
    }
}