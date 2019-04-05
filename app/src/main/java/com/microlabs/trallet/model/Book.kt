package com.microlabs.trallet.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val title: String = "",
        @ColumnInfo(name = "description")
        val desc: String = ""
)