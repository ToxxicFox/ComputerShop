package com.example.computershop.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.computershop.R
import com.example.computershop.databinding.FragmentLoginBinding
import com.example.computershop.network.AuthApi
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.requests.LoginRequestObject
import com.example.computershop.repositories.AuthRepository
import com.example.computershop.ui.base.BaseFragment

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun getViewModel() = AuthViewModel::class.java


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)


    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = AuthViewModel(getFragmentRepository())
            loginFragment = this@LoginFragment
        }
    }


    fun goToSignUp() {
        findNavController().navigate(R.id.action_navigation_login_to_navigation_sign_up)
    }


    fun login() {

        viewModel.token.observe(viewLifecycleOwner, Observer {
            when(it) {
                is ResultValue.Success -> {
                    viewModel.saveAuthToken(it.value)
                    findNavController().navigate(R.id.action_navigation_login_to_navigation_profile)
                }
                is ResultValue.Failure -> {
                    Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_SHORT).show()
                }
            }
        })

        val email = binding?.signEmail?.text.toString()
        val password = binding?.signPasswordLogin?.text.toString()
        val deviceName = android.os.Build.MODEL
        val requestLoginObject = LoginRequestObject(email, password, deviceName)

        viewModel.login(requestLoginObject)
    }

}