package com.codingwithjadrey.pasman.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.codingwithjadrey.pasman.data.entity.Pas
import com.codingwithjadrey.pasman.data.repo.PasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * MIT License
 *
 * Copyright (c) 2022 Isaac Jadrey Ongwara Jr
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@HiltViewModel
class PasViewModel @Inject constructor(private val repository: PasRepository) : ViewModel(){

    /** validates text input fields whether they are empty or not */
    fun validateInputs(account: String, password: String, accountName: String): Boolean {
        return !(account.isEmpty() || password.isEmpty() || accountName.isEmpty())
    }

    // actual action to get all passwords from the database using the repository
    val allPasswords = repository.pasFlow.asLiveData()
    // actual action to sort items by email from the database using the repository
    val sortByEmail = repository.sortByEmail.asLiveData()
    // actual action to sort items by social from the database using the repository
    val sortBySocial = repository.sortBySocial.asLiveData()
    // actual action to sort items by wallet from the database using the repository
    val sortByWallet = repository.sortByWallet.asLiveData()
    // actual action to sort items by app from the database using the repository
    val sortByApp = repository.sortByApp.asLiveData()
    // actual action to sort items by website from the database using the repository
    val sortByWebsite = repository.sortByWebsite.asLiveData()

    /** performs the actual item insertion into the database by calling in
     * the insert method from the repository*/
    fun insertPassword(pas: Pas) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertPassword(pas)
        }
    }

    /** performs the actual item update in the database by calling
     * the update method from the repository*/
    fun updatePassword(pas: Pas) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePassword(pas)
        }
    }

    /** performs the actual item deletion from the database by calling in
     * the delete method from the repository*/
    fun deletePassword(pas: Pas) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePassword(pas)
        }
    }

    /** performs the actual deletion of all items in the database by calling
     * the delete all method from the repository*/
    fun deleteAllPasswords() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllPasswords()
        }
    }

    /** carries out the search functionality from the database by calling search
     * from the repository*/
    fun searchPassword(searchQuery: String) = repository.searchPassword(searchQuery).asLiveData()
}