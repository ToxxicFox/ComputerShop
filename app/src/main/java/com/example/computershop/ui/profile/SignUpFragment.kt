package com.example.computershop.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.computershop.R
import com.example.computershop.databinding.FragmentSignUpBinding
import com.example.computershop.network.AuthApi
import com.example.computershop.network.ResultValue
import com.example.computershop.network.data.models.SignUpRequestObject
import com.example.computershop.repositories.AuthRepository
import com.example.computershop.ui.base.BaseFragment


class SignUpFragment : BaseFragment<AuthViewModel, FragmentSignUpBinding, AuthRepository>() {

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSignUpBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = AuthViewModel(getFragmentRepository())
            signUpFragment = this@SignUpFragment
        }
    }

    fun signUp() {

        viewModel.token.observe(viewLifecycleOwner, Observer {

            when(it) {
                is ResultValue.Success -> {
                    viewModel.saveAuthToken(it.value)
                    findNavController().navigate(R.id.action_navigation_sign_up_to_navigation_profile)
                }
                is ResultValue.Failure -> {
                    Toast.makeText(requireContext(), "Registration Failure", Toast.LENGTH_SHORT).show()
                }
            }

        })

        val email = binding?.signEmail?.text.toString()
        val password = binding?.signPassword?.text.toString()
        val passwordConfirmation = binding?.confirmSignPassword?.text.toString()
        val requestSignUpObject = SignUpRequestObject(email, password, passwordConfirmation)

        viewModel.signUp(requestSignUpObject)

    }

}