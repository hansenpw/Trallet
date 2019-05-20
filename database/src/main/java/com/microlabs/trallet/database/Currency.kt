package com.microlabs.trallet.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "currencies", indices = [Index(value = ["name"], unique = true)])
data class Currency(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val name: String,
        val value: Double
)