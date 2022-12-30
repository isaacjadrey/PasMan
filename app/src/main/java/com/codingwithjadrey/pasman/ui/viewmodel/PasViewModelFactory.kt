package com.codingwithjadrey.pasman.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codingwithjadrey.pasman.data.repo.PasRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class PasViewModelFactory @Inject constructor(private val repository: PasRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PasViewModel(repository) as T
    }
}