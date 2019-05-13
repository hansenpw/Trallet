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
            try {
                database.currencyDao().insertCurrency(currency)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateCurrency(currency: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                database.currencyDao().updateCurrency(currency)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteCurrency(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                database.currencyDao().deleteCurrency(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}