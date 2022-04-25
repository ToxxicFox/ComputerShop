package com.example.computershop.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.computershop.network.RemoteDataSource
import com.example.computershop.repositories.BaseRepository
import com.example.computershop.repositories.UserPreferences

abstract class BaseFragment<tViewModel: ViewModel, binding: ViewBinding, repository: BaseRepository>
    : Fragment() {

    protected var binding: binding? = null
    protected lateinit var viewModel: tViewModel
    protected val remoteDataSource = RemoteDataSource()
    protected lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPreferences = UserPreferences(requireContext())
        binding = getFragmentBinding(inflater, container)
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())
        return binding!!.root
    }

    abstract fun getViewModel() : Class<tViewModel>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) : binding

    abstract fun getFragmentRepository(): repository

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}