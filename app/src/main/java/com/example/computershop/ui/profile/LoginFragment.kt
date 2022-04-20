package com.example.computershop.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.computershop.R
import com.example.computershop.databinding.FragmentLoginBinding
import com.example.computershop.network.AuthApi
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.LoginRequestObject
import com.example.computershop.repositories.AuthRepository
import com.example.computershop.ui.base.BaseFragment
import com.example.computershop.ui.base.ViewModelFactory
import kotlinx.coroutines.launch

const val TAG = "LOGIN_FRAGMENT"

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun getViewModel() = AuthViewModel::class.java


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)


    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java))


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            // Specify the fragment as the lifecycle owner
            lifecycleOwner = viewLifecycleOwner
            viewModel = AuthViewModel(getFragmentRepository())
            loginFragment = this@LoginFragment
        }
    }


    fun goToSignUp() {
        findNavController().navigate(R.id.action_navigation_login_to_navigation_sign_up)
    }


    fun login() {

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when(it) {
                is ResultValue.Success -> {
                    lifecycleScope.launch {
                        userPreferences.saveAuthToken(it.value)
                    }

                }
                is ResultValue.Failure -> {
                    Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_SHORT).show()
                }
            }
        })

        val email = binding.signEmail.text.toString()
        val password = binding.signPasswordLogin.text.toString()
        val deviceName = android.os.Build.MODEL
        val requestObject = LoginRequestObject(email, password, deviceName)

        viewModel.login(requestObject)
    }

}