package com.microlabs.trallet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.microlabs.trallet.model.Expense
import com.microlabs.trallet.repo.AppDatabase
import com.microlabs.trallet.repo.ExpenseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application): AndroidViewModel(application) {

    private val expenseRepository: ExpenseDao by lazy { AppDatabase.getInstance(getApplication()).expenseDao() }

    fun loadExpensesByBookId(id: Int) = expenseRepository.getAllExpensesByBookId(id)

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch(Dispatchers.IO) {
            expenseRepository.deleteExpense(expense)
        }
    }
}