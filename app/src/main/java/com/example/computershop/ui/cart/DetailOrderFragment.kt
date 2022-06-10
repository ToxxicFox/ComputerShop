package com.example.computershop.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.computershop.R
import com.example.computershop.databinding.FragmentDetailOrderBinding
import com.example.computershop.network.ResultValue
import com.example.computershop.network.ShopApi
import com.example.computershop.repositories.OrderRepository
import com.example.computershop.ui.adapters.DetailOrderViewAdapter
import com.example.computershop.ui.base.BaseFragment

class DetailOrderFragment :
    BaseFragment<DetailOrderViewModel, FragmentDetailOrderBinding, OrderRepository>() {

    private val detailOrderViewAdapter = DetailOrderViewAdapter()

    override fun getViewModel() = DetailOrderViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailOrderBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        OrderRepository(remoteDataSource.buildApi(ShopApi::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getOrderInfo()
        initDetailOrderViewAdapter()
        displayOrderProductList()
        toProfile()
    }

    private fun getOrderInfo() {
        arguments?.getString("orderId")?.let { viewModel.getOrderId(it) }
        arguments?.getString("token")?.let { viewModel.getToken(it) }
        arguments?.getString("orderName")?.let { viewModel.getOrderName(it) }
    }

    private fun initDetailOrderViewAdapter() {
        val orderListView = binding?.orderItemsList
        orderListView?.layoutManager =
            LinearLayoutManager(activity)
        orderListView?.adapter = detailOrderViewAdapter
    }

    private fun displayOrderProductList() {
        viewModel.getOrderDetails()
        viewModel.orderProductList.observe(viewLifecycleOwner) {
            when (it) {
                is ResultValue.Success -> {
                    detailOrderViewAdapter.setUpdateCart(it.value.data.items)
                    binding?.orderNumber?.text = viewModel.orderNumber
                    binding?.customerName?.text = it.value.data.fio
                    binding?.customerAddress?.text = it.value.data.address
                    binding?.customerPhoneNumber?.text = it.value.data.phone
                    binding?.orderStatus?.text = it.value.data.status
                    binding?.totalOrderPrice?.text = it.value.data.total.toString()
                    binding?.totalOrderPriceWithDiscount?.text =
                        it.value.data.total_with_discount.toString()
                }
                is ResultValue.Failure -> {
                    Toast.makeText(requireContext(),
                        "Упс... Что-то пошло не так",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun toProfile() {
        binding?.toCustomerProfile?.setOnClickListener {
            findNavController()
                .navigate(R.id.action_navigation_detail_order_fragment_to_navigation_profile)
        }
    }

}