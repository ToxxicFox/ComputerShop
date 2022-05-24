package com.example.computershop.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.computershop.databinding.CartFragmentBinding
import com.example.computershop.network.ResultValue
import com.example.computershop.network.ShopApi
import com.example.computershop.repositories.CartRepository
import com.example.computershop.ui.adapters.CartViewAdapter
import com.example.computershop.ui.base.BaseFragment

class CartFragment: BaseFragment<CartViewModel, CartFragmentBinding, CartRepository>(){

    private val cartAdapter = CartViewAdapter()

    override fun getViewModel() = CartViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = CartFragmentBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        CartRepository(remoteDataSource.buildApi(ShopApi::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkToken()
        initCartAdapter()
        displayCartProductList()
    }

    private fun initCartAdapter() {
        val cartListView = binding?.cartList
        cartListView?.layoutManager =
            LinearLayoutManager(activity)
        cartListView?.adapter = cartAdapter
    }

    private fun displayCartProductList() {
        viewModel.cartList.observe(viewLifecycleOwner) {
            when (it) {
                is ResultValue.Success -> {
                    cartAdapter.setUpdateCart(it.value.data)
                }
                is ResultValue.Failure -> {
                    Toast.makeText(requireContext(), "List is empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkToken() {
        userPreferences.authToken.asLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                viewModel.getCart(it)
            } else {
                Toast.makeText(requireContext(),
                    "Пожалуйста авторизуйтесь",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

}