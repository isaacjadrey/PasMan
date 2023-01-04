package com.codingwithjadrey.pasman.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codingwithjadrey.pasman.data.repo.UserRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory @Inject constructor(private val repository: UserRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(repository) as T
    }
}