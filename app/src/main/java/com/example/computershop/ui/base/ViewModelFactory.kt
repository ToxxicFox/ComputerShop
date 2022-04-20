package com.example.computershop.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.computershop.repositories.AuthRepository
import com.example.computershop.repositories.BaseRepository
import com.example.computershop.ui.profile.AuthViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->
                AuthViewModel(repository as AuthRepository) as T
            else -> throw IllegalArgumentException("ViewModel not found")
        }

    }

}