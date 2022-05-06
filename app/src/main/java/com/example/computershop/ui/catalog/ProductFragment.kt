package com.example.computershop.ui.catalog

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.computershop.databinding.FragmentProductBinding
import com.example.computershop.network.ShopApi
import com.example.computershop.network.data.models.responses.products.ProductData
import com.example.computershop.repositories.CatalogRepository
import com.example.computershop.ui.base.BaseFragment
import com.google.gson.Gson

private const val EXT = ".jpg"
private const val RUB = "₽"

class ProductFragment :
    BaseFragment<CatalogViewModel, FragmentProductBinding, CatalogRepository>() {

    override fun getViewModel() = CatalogViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProductBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        CatalogRepository(remoteDataSource.buildApi(ShopApi::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productData = arguments?.getString("ProductItem")
        val productItem = Gson().fromJson(productData, ProductData::class.java)
        val url = productItem.img + EXT

        binding?.pageProductTitle?.text = productItem.title
        binding?.pageProductPrice?.text = productItem.price.toString() + RUB
        binding?.productPageInfo?.text = productItem.info
        Glide.with(binding?.productPageImg!!)
            .load(url)
            .into(binding?.productPageImg!!)

    }

}
