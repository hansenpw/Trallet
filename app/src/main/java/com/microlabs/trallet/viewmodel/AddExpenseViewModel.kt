package com.microlabs.trallet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.microlabs.trallet.model.Expense
import com.microlabs.trallet.repo.AppDatabase

class AddExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val database: AppDatabase by lazy { AppDatabase.getInstance(getApplication()) }

    fun getAllCurrencies() = database.currencyDao().getAllCurrencies()

    fun getAllCategory() = database.categoryDao().getAllCategories()

    fun insertExpense(expense: Expense) = database.expenseDao().insertExpense(expense)

    fun updateExpense(expense: Expense) = database.expenseDao().updateExpense(expense)

    fun getExpenseById(id: Int) = database.expenseDao().getExpenseById(id)
}