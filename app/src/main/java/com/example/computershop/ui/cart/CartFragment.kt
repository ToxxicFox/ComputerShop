package com.example.computershop.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.computershop.R
import com.example.computershop.databinding.CartFragmentBinding
import com.example.computershop.network.ResultValue
import com.example.computershop.network.ShopApi
import com.example.computershop.repositories.CartRepository
import com.example.computershop.ui.adapters.CartViewAdapter
import com.example.computershop.ui.base.BaseFragment
import com.google.gson.Gson

class CartFragment: BaseFragment<CartViewModel, CartFragmentBinding, CartRepository>(){

    private val cartAdapter = CartViewAdapter(deleteItem = ::deleteCartItem,
                                                incQuantity = ::increaseItemQuantity,
                                                decQuantity = ::reduceItemQuantity)

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
        updateCart()

        binding?.buyButton?.setOnClickListener {
            toOrderFragment()
        }
    }

    private fun initCartAdapter() {
        val cartListView = binding?.cartList
        cartListView?.layoutManager =
            LinearLayoutManager(activity)
        cartListView?.adapter = cartAdapter
    }

    private fun deleteCartItem(basketId: Int) {
        viewModel.deleteItemFromCart(basketId)
    }

    private fun increaseItemQuantity(basketItemId: Int, quantity: Int) {
        viewModel.increaseQuantity(quantity)
        viewModel.changeQuantityItemInBasket(basketItemId)
    }

    private fun reduceItemQuantity(basketItemId: Int, quantity: Int) {
        viewModel.reduceQuantity(basketItemId, quantity)
        viewModel.changeQuantityItemInBasket(basketItemId)
    }

    private fun toOrderFragment() {
        viewModel.cartList.observe(viewLifecycleOwner) {
            when (it) {
                is ResultValue.Success -> {
                    if (it.value.meta.total == 0) {
                        Toast.makeText(requireContext(),
                            "Ваша корзина пуста",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        val bundle = Bundle()
                        bundle.putString("CartInfo", Gson().toJson(it.value))
                        bundle.putString("Token", viewModel.token)
                        findNavController()
                            .navigate(R.id.action_navigation_cart_to_navigation_order_fragment, bundle)
                    }
                }
                is ResultValue.Failure -> {
                    Toast.makeText(requireContext(),
                        it.errorCode.toString(),
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkToken() {
        userPreferences.authToken.asLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                viewModel.getToken(it)
                viewModel.getCart(it)
            } else {
                Toast.makeText(requireContext(),
                    "Пожалуйста авторизуйтесь",
                    Toast.LENGTH_SHORT).show()
                binding?.buyButton?.visibility = View.GONE
            }
        }
    }

    private fun displayCartProductList() {
        viewModel.cartList.observe(viewLifecycleOwner) {
            when (it) {
                is ResultValue.Success -> {
                    cartAdapter.setUpdateCart(it.value.data)
                }
                is ResultValue.Failure -> {
                    Toast.makeText(requireContext(),
                        "Упс... Что-то пошло не так",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateCart() {
        viewModel.cartListAfterChanges.observe(viewLifecycleOwner) {
            when (it) {
                is ResultValue.Success -> {
                    Toast.makeText(requireContext(), it.value.message, Toast.LENGTH_SHORT).show()
                }
                is ResultValue.Failure -> {
                    Toast.makeText(requireContext(),
                        it.errorCode.toString(),
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}