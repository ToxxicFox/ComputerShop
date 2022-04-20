package com.example.computershop.ui.catalog

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.computershop.R

class CatalogFragment : Fragment() {

    companion object {
        fun newInstance() = CatalogFragment()
    }

    private lateinit var viewModel: CatalogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.catalog_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CatalogViewModel::class.java)
        // TODO: Use the ViewModel
    }

}