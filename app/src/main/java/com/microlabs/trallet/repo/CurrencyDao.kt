package com.microlabs.trallet.repo

import androidx.lifecycle.LiveData
import androidx.room.*
import com.microlabs.trallet.model.Currency

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currencies")
    fun getAllCurrencies(): LiveData<List<Currency>>

    @Insert
    suspend fun insertCurrency(vararg currency: Currency)

    @Update
    suspend fun updateCurrency(currency: Currency)

    @Delete
    suspend fun deleteCurrency(currency: Currency)
}