package com.microlabs.trallet.repo

import androidx.lifecycle.LiveData
import androidx.room.*
import com.microlabs.trallet.model.Expense

@Dao
interface ExpenseDao {

    @Insert
    fun insertExpense(expense: Expense)

    @Update
    fun updateExpense(expense: Expense)

    @Delete
    fun deleteExpense(vararg expense: Expense)

    @Query("SELECT * FROM expenses WHERE bookId = :id")
    fun getAllExpensesByBookId(id: Int): LiveData<List<Expense>>

    @Query("SELECT DISTINCT * FROM expenses WHERE id = :id")
    fun getExpenseById(id: Int): LiveData<Expense>
}