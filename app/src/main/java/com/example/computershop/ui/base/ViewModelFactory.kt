package com.example.computershop.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.computershop.repositories.*
import com.example.computershop.ui.cart.CartViewModel
import com.example.computershop.ui.cart.DetailOrderViewModel
import com.example.computershop.ui.catalog.CatalogViewModel
import com.example.computershop.ui.catalog.ProductViewModel
import com.example.computershop.ui.cart.OrderViewModel
import com.example.computershop.ui.profile.AuthViewModel
import com.example.computershop.ui.profile.ProfileViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->
                AuthViewModel(repository as AuthRepository) as T

            modelClass.isAssignableFrom(CatalogViewModel::class.java) ->
                CatalogViewModel(repository as CatalogRepository) as T

            modelClass.isAssignableFrom(CartViewModel::class.java) ->
                CartViewModel(repository as CartRepository) as T

            modelClass.isAssignableFrom(ProductViewModel::class.java) ->
                ProductViewModel(repository as ProductRepository) as T

            modelClass.isAssignableFrom(OrderViewModel::class.java) ->
                OrderViewModel(repository as OrderRepository) as T

            modelClass.isAssignableFrom(DetailOrderViewModel::class.java) ->
                DetailOrderViewModel(repository as OrderRepository) as T

            modelClass.isAssignableFrom(ProfileViewModel::class.java) ->
                ProfileViewModel(repository as ProfileRepository) as T

            else -> throw IllegalArgumentException("ViewModel not found")
        }
    }

}