package com.microlabs.trallet.repo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.microlabs.trallet.model.Currency

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currencies")
    fun getAllCurrencies(): LiveData<List<Currency>>

    @Insert
    suspend fun insertCurrency(vararg currency: Currency)
}