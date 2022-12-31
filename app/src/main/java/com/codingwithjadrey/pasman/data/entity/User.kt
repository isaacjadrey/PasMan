package com.codingwithjadrey.pasman.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codingwithjadrey.pasman.util.Constants.USER_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = USER_TABLE)
data class User(
    // we shall be keeping only one user
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    var password: String
): Parcelable
