package com.microlabs.trallet.model

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val name: String,
        @ColumnInfo(name = "imageId")
        @DrawableRes
        val imgId: Int
)