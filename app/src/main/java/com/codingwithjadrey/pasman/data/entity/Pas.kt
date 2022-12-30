package com.codingwithjadrey.pasman.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codingwithjadrey.pasman.util.Constants.PAS_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = PAS_TABLE)
data class Pas(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var account: String,
    var accountPassword: String,
    var accountName: String,
    var accountType: String
) : Parcelable