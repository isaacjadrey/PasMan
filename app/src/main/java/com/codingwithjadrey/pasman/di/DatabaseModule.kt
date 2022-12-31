package com.codingwithjadrey.pasman.di

import android.content.Context
import androidx.room.Room
import com.codingwithjadrey.pasman.data.database.PasDatabase
import com.codingwithjadrey.pasman.util.Constants.PAS_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(app, PasDatabase::class.java, PAS_DATABASE)
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providePasDao(database: PasDatabase) = database.pasDao

    @Singleton
    @Provides
    fun provideUserDao(database: PasDatabase) = database.userDao
}