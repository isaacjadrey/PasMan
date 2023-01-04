package com.codingwithjadrey.pasman.ui.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithjadrey.pasman.data.entity.User
import com.codingwithjadrey.pasman.data.repo.UserRepository
import com.codingwithjadrey.pasman.util.StateListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel(), Observable {

    var stateListener: StateListener? = null

    @Bindable
    val loginPassword = MutableLiveData<String>()
    val changePassword = MutableLiveData<String>()

    /** actual function to insert user into user database */
    fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUser(user)
        }
    }

    /** validates password entries */
    fun validatePasswords(oldPassword: String, newPassword: String): Boolean {
        return !(oldPassword.isEmpty() || newPassword.isEmpty())
    }

    /** actual function to update user in user database */
    fun updateUser(user: User) {
        if (changePassword.value.isNullOrEmpty()) {
            stateListener?.onError("New password is required")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    /** actual method to delete user from the user database */
    fun deleteUser() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser()
        }
    }

    /** method check whether user exists and login with user
     * or else error
     */
    fun loginUser() {
        if (loginPassword.value.isNullOrEmpty()) {
            stateListener?.onError("Enter Login Password")
            return
        }
        viewModelScope.launch {
            try {
                val userResponse = repository.loginUser(loginPassword.value!!)

                userResponse.collect { user ->
                    if (user == null) {
                        stateListener?.onError("No login credentials found. It seems you have not created a login password")
                        return@collect
                    } else {
                        stateListener?.onSuccess("Login Credential validated with ${user.password}")
                        return@collect
                    }
                }
            } catch (e: Exception) {
                stateListener?.onError(e.message!!)
                return@launch
            }
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}