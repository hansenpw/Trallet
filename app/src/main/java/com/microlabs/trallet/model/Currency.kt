package com.microlabs.trallet.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class Currency(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val name: String,
        val value: Double
)