package com.codingwithjadrey.pasman.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codingwithjadrey.pasman.data.dao.PasDao
import com.codingwithjadrey.pasman.data.entity.Pas

@Database(entities = [Pas::class], version = 1, exportSchema = false)
abstract class PasDatabase : RoomDatabase() {
    abstract val pasDao: PasDao
}