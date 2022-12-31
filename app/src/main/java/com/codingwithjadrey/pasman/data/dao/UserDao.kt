package com.codingwithjadrey.pasman.data.dao

import androidx.room.*
import com.codingwithjadrey.pasman.data.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // statement inserts user into the user database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    // statement updates user in the user database
    @Update
    suspend fun updateUser(user: User)

    // statement deletes user from the user database
    @Query("DELETE FROM user_table")
    suspend fun deleteUser()

    // statement checks for the user password in the user database
    @Query("SELECT * FROM user_table WHERE password LIKE :userPassword")
    fun loginUser(userPassword: String): Flow<User>
}