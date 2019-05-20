package com.microlabs.trallet.repo

import androidx.lifecycle.LiveData
import androidx.room.*
import com.microlabs.trallet.database.Expense

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertExpense(expense: Expense)

    @Update
    suspend fun updateExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(vararg expense: Expense)

    @Query("SELECT * FROM expenses WHERE bookId = :id")
    fun getAllExpensesByBookId(id: Int): LiveData<List<Expense>>

    @Query("SELECT DISTINCT * FROM expenses WHERE id = :id")
    fun getExpenseById(id: Int): LiveData<Expense>
}