package com.codingwithjadrey.pasman.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codingwithjadrey.pasman.data.dao.PasDao
import com.codingwithjadrey.pasman.data.dao.UserDao
import com.codingwithjadrey.pasman.data.entity.Pas
import com.codingwithjadrey.pasman.data.entity.User

@Database(entities = [Pas::class, User::class], version = 2, exportSchema = false)
abstract class PasDatabase : RoomDatabase() {
    abstract val pasDao: PasDao
    abstract val userDao: UserDao
}