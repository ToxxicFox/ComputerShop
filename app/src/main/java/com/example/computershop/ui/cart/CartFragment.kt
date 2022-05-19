package com.example.computershop.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.computershop.databinding.CartFragmentBinding
import com.example.computershop.network.ShopApi
import com.example.computershop.repositories.CartRepository
import com.example.computershop.ui.base.BaseFragment

class CartFragment: BaseFragment<CartViewModel, CartFragmentBinding, CartRepository>(){
    override fun getViewModel() = CartViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = CartFragmentBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        CartRepository(remoteDataSource.buildApi(ShopApi::class.java))
}