package com.example.computershop.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.computershop.databinding.CatalogFragmentBinding
import com.example.computershop.network.ResultValue
import com.example.computershop.network.ShopApi
import com.example.computershop.repositories.CatalogRepository
import com.example.computershop.ui.adapters.CategoryViewAdapter
import com.example.computershop.ui.adapters.ProductViewAdapter
import com.example.computershop.ui.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CatalogFragment : BaseFragment<CatalogViewModel, CatalogFragmentBinding, CatalogRepository>() {

    private val categoryAdapter = CategoryViewAdapter()
    private val productAdapter = ProductViewAdapter()

    override fun getViewModel() = CatalogViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = CatalogFragmentBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        CatalogRepository(remoteDataSource.buildApi(ShopApi::class.java))


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCategoryAdapter()
        initProductAdapter()
        displayCategoryList()
        displayProductList()
    }

    private fun initCategoryAdapter() {
        val listView = binding?.filterList
        listView?.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        listView?.adapter = categoryAdapter
    }

    private fun initProductAdapter() {
        val productView = binding?.productsList
        productView?.layoutManager =
            GridLayoutManager(activity, 2)
        productView?.adapter = productAdapter
    }

    private fun displayCategoryList() {

        viewModel.getCategories()

        viewModel.categoryList.observe(viewLifecycleOwner, Observer {
            when(it) {
                is ResultValue.Success -> {
                    categoryAdapter.setUpdateCategory(it.value.data)
                }
                is ResultValue.Failure -> {
                    Toast.makeText(requireContext(), "List is empty", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun displayProductList() {

        lifecycleScope.launchWhenCreated {
            viewModel.getProducts().collectLatest {
                productAdapter.submitData(it)
            }
        }

    }

}