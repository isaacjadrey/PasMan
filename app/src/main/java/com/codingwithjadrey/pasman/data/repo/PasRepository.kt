package com.codingwithjadrey.pasman.data.repo

import com.codingwithjadrey.pasman.data.database.PasDatabase
import com.codingwithjadrey.pasman.data.entity.Pas
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PasRepository @Inject constructor(pasDatabase: PasDatabase) {

    /** reference to the pasDao obtainable from the pas database */
    private val pasDao = pasDatabase.pasDao

    /** method that gets all items in the database */
    val pasFlow: Flow<List<Pas>> get() = pasDao.allPasswords()

    /** method that sorts items by email from the database */
    val sortByEmail: Flow<List<Pas>> = pasDao.sortByEmail()

    /** method that sorts items by social account from the database */
    val sortBySocial: Flow<List<Pas>> = pasDao.sortBySocial()

    /** method that sorts items by wallet from the database */
    val sortByWallet: Flow<List<Pas>> = pasDao.sortByWallet()

    /** method that sorts items by app from the database */
    val sortByApp: Flow<List<Pas>> = pasDao.sortByApp()

    /** method that sorts items by website from the database */
    val sortByWebsite: Flow<List<Pas>> = pasDao.sortByWebsite()

    /** method to insert items in the database */
    suspend fun insertPassword(pas: Pas) = pasDao.insertPas(pas)

    /** method to update an item in the database */
    suspend fun updatePassword(pas: Pas) = pasDao.updatePas(pas)

    /** method to delete an item in the database */
    suspend fun deletePassword(pas: Pas) = pasDao.deletePas(pas)

    /** method tp delete all items in the database */
    suspend fun deleteAllPasswords() = pasDao.deleteAllPasswords()

    /** method to search items in the database based on search query */
    fun searchPassword(searchQuery: String) = pasDao.searchPassword(searchQuery)
}