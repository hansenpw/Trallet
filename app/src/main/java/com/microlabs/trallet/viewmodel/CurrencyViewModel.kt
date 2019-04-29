package com.microlabs.trallet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.microlabs.trallet.repo.AppDatabase

class CurrencyViewModel(application: Application) : AndroidViewModel(application) {

    fun getAllCurrencies() = AppDatabase.getInstance(getApplication()).currencyDao().getAllCurrencies()
}