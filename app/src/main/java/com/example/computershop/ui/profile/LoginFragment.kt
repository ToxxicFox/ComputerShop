package com.example.computershop.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.computershop.R
import com.example.computershop.databinding.FragmentLoginBinding
import com.example.computershop.network.AuthApi
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.requests.LoginRequest
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
            loginFragment = this@LoginFragment
        }
        startScreen()
    }

    fun goToSignUp() {
        findNavController().navigate(R.id.action_navigation_login_to_navigation_sign_up)
    }

    fun login() {

        viewModel.token.observe(viewLifecycleOwner) {
            when (it) {
                is ResultValue.Success -> {
                    viewModel.saveAuthToken(it.value)
                }
                is ResultValue.Failure -> {
                    Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val email = binding?.signEmail?.text.toString()
        val password = binding?.signPasswordLogin?.text.toString()
        val deviceName = android.os.Build.MODEL
        val requestLogin = LoginRequest(email, password, deviceName)

        viewModel.login(requestLogin)
    }

    private fun startScreen() {
        userPreferences.authToken.asLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().navigate(R.id.action_navigation_login_to_navigation_profile)
            }
        }
    }

}