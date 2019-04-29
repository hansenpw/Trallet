package com.microlabs.trallet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.microlabs.trallet.model.Currency
import com.microlabs.trallet.repo.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCurrencyViewModel(application: Application) : AndroidViewModel(application) {

    private val database: AppDatabase by lazy { AppDatabase.getInstance(getApplication()) }

    fun insertCurrency(currency: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            database.currencyDao().insertCurrency(currency)
        }
    }

    fun updateCurrency(currency: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            database.currencyDao().updateCurrency(currency)
        }
    }

    fun deleteCurrency(currency: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            database.currencyDao().deleteCurrency(currency)
        }
    }
}