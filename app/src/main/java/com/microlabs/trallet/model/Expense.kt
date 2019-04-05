package com.microlabs.trallet.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "expenses",
        foreignKeys = [
            ForeignKey(entity = Book::class, parentColumns = ["id"], childColumns = ["bookId"], onDelete = ForeignKey.CASCADE),
            ForeignKey(entity = Category::class, parentColumns = ["id"], childColumns = ["categoryId"]),
            ForeignKey(entity = Currency::class, parentColumns = ["id"], childColumns = ["currencyId"], onDelete = ForeignKey.RESTRICT)
        ])
data class Expense(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val title: String,
        var value: Double,
        var categoryId: Int,
        var currencyId: Int,
        var bookId: Int,
        var details: String = "",
        var date: Long = Date().time
)