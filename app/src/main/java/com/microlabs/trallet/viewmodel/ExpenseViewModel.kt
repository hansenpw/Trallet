package com.microlabs.trallet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.microlabs.trallet.repo.AppDatabase
import com.microlabs.trallet.repo.ExpenseDao

class ExpenseViewModel(application: Application): AndroidViewModel(application) {

    private val expenseRepository: ExpenseDao by lazy { AppDatabase.getInstance(getApplication()).expenseDao() }

    fun loadExpensesByBookId(id: Int) = expenseRepository.getAllExpensesByBookId(id)
}