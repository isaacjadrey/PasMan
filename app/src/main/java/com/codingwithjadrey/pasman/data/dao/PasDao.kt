package com.codingwithjadrey.pasman.data.dao

import androidx.room.*
import com.codingwithjadrey.pasman.data.entity.Pas
import kotlinx.coroutines.flow.Flow

@Dao
interface PasDao {

    /** statement to insert items in the database */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPas(pas: Pas)

    /** statement to update an item in the database */
    @Update
    suspend fun updatePas(pas: Pas)

    /** statement to delete an item in the database */
    @Delete
    suspend fun deletePas(pas: Pas)

    /** statement to delete all items in the database */
    @Query("DELETE FROM password_table")
    suspend fun deleteAllPasswords()

    /** statement to get all items in the database */
    @Query("SELECT * FROM password_table")
    fun allPasswords(): Flow<List<Pas>>

    /** statement to search for items in the database based on search keyword */
    @Query("SELECT * FROM password_table WHERE accountName LIKE :searchQuery")
    fun searchPassword(searchQuery: String): Flow<List<Pas>>

    /** statement to sort items in the database by email */
    @Query(
        "SELECT * FROM password_table ORDER BY CASE " +
                "WHEN accountType LIKE 'email%' THEN 1 " +
                "WHEN accountType LIKE 'socialAccount%' THEN 2 " +
                "WHEN accountType LIKE 'wallet%' THEN 3 " +
                "WHEN accountType LIKE 'app%' THEN 4 " +
                "WHEN accountType LIKE 'website%' THEN 5 END"
    )
    fun sortByEmail(): Flow<List<Pas>>

    /** statement to sort items in the database by social account */
    @Query(
        "SELECT * FROM password_table ORDER BY CASE " +
                "WHEN accountType LIKE 'socialAccount%' THEN 1 " +
                "WHEN accountType LIKE 'wallet%' THEN 2 " +
                "WHEN accountType LIKE 'app%' THEN 3 " +
                "WHEN accountType LIKE 'website%' THEN 4 " +
                "WHEN accountType LIKE 'email%' THEN 5 END"
    )
    fun sortBySocial(): Flow<List<Pas>>

    /** statement to sort items in the database by wallet */
    @Query(
        "SELECT * FROM password_table ORDER BY CASE " +
                "WHEN accountType LIKE 'wallet%' THEN 1 " +
                "WHEN accountType LIKE 'app%' THEN 2 " +
                "WHEN accountType LIKE 'website%' THEN 3 " +
                "WHEN accountType LIKE 'email%' THEN 4 " +
                "WHEN accountType LIKE 'socialAccount%' THEN 5 END"
    )
    fun sortByWallet(): Flow<List<Pas>>

    /** statement to sort items in the database by app */
    @Query(
        "SELECT * FROM password_table ORDER BY CASE " +
                "WHEN accountType LIKE 'app%' THEN 1 " +
                "WHEN accountType LIKE 'website%' THEN 2 " +
                "WHEN accountType LIKE 'email%' THEN 3 " +
                "WHEN accountType LIKE 'socialAccount%' THEN 4 " +
                "WHEN accountType LIKE 'wallet%' THEN 5 END"
    )
    fun sortByApp(): Flow<List<Pas>>

    /** statement to sort items in the database by website */
    @Query(
        "SELECT * FROM password_table ORDER BY CASE " +
                "WHEN accountType LIKE 'website%' THEN 1 " +
                "WHEN accountType LIKE 'email%' THEN 2 " +
                "WHEN accountType LIKE 'socialAccount%' THEN 3 " +
                "WHEN accountType LIKE 'wallet%' THEN 4 " +
                "WHEN accountType LIKE 'app%' THEN 5 END"
    )
    fun sortByWebsite(): Flow<List<Pas>>
}