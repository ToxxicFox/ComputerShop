package com.example.computershop.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.computershop.R
import com.example.computershop.databinding.FragmentOrderBinding
import com.example.computershop.network.ResultValue
import com.example.computershop.network.ShopApi
import com.example.computershop.network.data.models.requests.CreateOrderRequestModel
import com.example.computershop.network.data.models.responses.cart.CartResponse
import com.example.computershop.repositories.OrderRepository
import com.example.computershop.ui.base.BaseFragment
import com.google.gson.Gson

class OrderFragment: BaseFragment<OrderViewModel, FragmentOrderBinding, OrderRepository>(){

    override fun getViewModel() = OrderViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOrderBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        OrderRepository(remoteDataSource.buildApi(ShopApi::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
    }

    private fun bind() {
        val cartInfoJson = arguments?.getString("CartInfo")
        val cartInfo = Gson().fromJson(cartInfoJson, CartResponse::class.java)

        binding?.totalPrice?.text = cartInfo.meta.total.toString()
        binding?.totalDiscount?.text = cartInfo.meta.total_discount.toString()
        binding?.totalWithDiscount?.text = cartInfo.meta.total_with_discount.toString()

        binding?.createOrder?.setOnClickListener {
            createOrder()
        }
    }

    private fun createOrder() {
        arguments?.getString("Token")?.let { viewModel.getToken(it) }

        val address = binding?.clientAddress?.text.toString()
        val name = binding?.clientName?.text.toString()
        val number = binding?.clientPhone?.text.toString()
        val createOrderRequest = CreateOrderRequestModel(address, name, number)
        viewModel.createOrder(createOrderRequest)

        viewModel.cartList.observe(viewLifecycleOwner) {
            when(it) {
                is ResultValue.Success -> {
                    if (it.value.success) {
                        viewModel.getOrderId(it.value.message)
                        Toast.makeText(requireContext(), it.value.message, Toast.LENGTH_SHORT).show()
                        findNavController()
                            .navigate(
                                R.id.action_navigation_order_fragment_to_navigation_detail_order_fragment,
                                viewModel.bundle)
                    } else {
                        Toast.makeText(requireContext(), it.value.message, Toast.LENGTH_SHORT).show()
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
}