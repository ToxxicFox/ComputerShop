package com.example.computershop.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.computershop.R
import com.example.computershop.databinding.ProfileFragmentBinding
import com.example.computershop.network.ResultValue
import com.example.computershop.network.ShopApi
import com.example.computershop.repositories.ProfileRepository
import com.example.computershop.ui.adapters.ProfileOrdersViewAdapter
import com.example.computershop.ui.base.BaseFragment

class ProfileFragment :
    BaseFragment<ProfileViewModel, ProfileFragmentBinding, ProfileRepository>() {

    private val profileOrderViewAdapter = ProfileOrdersViewAdapter()

    override fun getViewModel() = ProfileViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = ProfileFragmentBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        ProfileRepository(remoteDataSource.buildApi(ShopApi::class.java), userPreferences)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkToken()
        initProfileOrderAdapter()
        displayProfileProductList()
        logout()
    }

    private fun initProfileOrderAdapter() {
        val profileOrderList = binding?.customerOrderList
        profileOrderList?.layoutManager =
            LinearLayoutManager(activity)
        profileOrderList?.adapter = profileOrderViewAdapter
    }

    private fun checkToken() {
        userPreferences.authToken.asLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                viewModel.getOrders(it)
            } else {
                findNavController().navigate(R.id.action_navigation_profile_to_navigation_login)
            }
        }
    }

    private fun displayProfileProductList() {
        viewModel.orderProductListLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResultValue.Success -> {
                    profileOrderViewAdapter.setUpdateProfile(it.value.data)
                }
                is ResultValue.Failure -> {
                    Toast.makeText(requireContext(),
                        "??????... ??????-???? ?????????? ???? ??????",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun logout() {
        binding?.exitBtn?.setOnClickListener {
            viewModel.logout()
        }
    }

}