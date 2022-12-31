package com.codingwithjadrey.pasman.data.repo

import com.codingwithjadrey.pasman.data.database.PasDatabase
import com.codingwithjadrey.pasman.data.entity.User
import javax.inject.Inject

class UserRepository @Inject constructor(database: PasDatabase) {

    /** references userDao form the database class */
    private val userDao = database.userDao

    /** inserts user into the user database */
    suspend fun insertUser(user: User) = userDao.insertUser(user)

    /** updates user info in the database */
    suspend fun updateUser(user: User) = userDao.updateUser(user)

    /** deletes user info from the database */
    suspend fun deleteUser() = userDao.deleteUser()

    /** logs in the user based on the user password */
    fun loginUser(userPassword: String) = userDao.loginUser(userPassword)
}