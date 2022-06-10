package com.example.computershop.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.computershop.databinding.FragmentProductBinding
import com.example.computershop.network.ShopApi
import com.example.computershop.network.data.models.responses.products.ProductData
import com.example.computershop.repositories.ProductRepository
import com.example.computershop.ui.base.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

private const val EXT = ".jpg"
private const val RUB = "₽"

class ProductFragment :
    BaseFragment<ProductViewModel, FragmentProductBinding, ProductRepository>() {

    override fun getViewModel() = ProductViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProductBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        ProductRepository(remoteDataSource.buildApi(ShopApi::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productData = arguments?.getString("ProductItem")
        val productItem = Gson().fromJson(productData, ProductData::class.java)
        viewModel.setProductRequest(productItem)
        val url = productItem.img + EXT

        binding?.pageProductTitle?.text = productItem.title
        binding?.pageProductPrice?.text = productItem.price.toString() + RUB
        binding?.productPageInfo?.text = productItem.info
        Glide.with(binding?.productPageImg!!)
            .load(url)
            .into(binding?.productPageImg!!)

        binding?.addToCart?.setOnClickListener {
            addToCart()
        }

    }

    private fun addToCart() {
        userPreferences.authToken.asLiveData().observe(viewLifecycleOwner){
            if (it != null) {
                lifecycleScope.launchWhenCreated {
                    viewModel.productRequest?.let { item -> viewModel.addToCart(it, item) }
                    Toast.makeText(requireContext(), viewModel.messageAdding, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(),
                    "Пожалуйста авторизуйтесь",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

}
