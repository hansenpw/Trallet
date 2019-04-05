package com.microlabs.trallet.view

import com.microlabs.trallet.model.Currency

/**
 * Created by Hansen on 5/11/2017.
 *
 * RCurrency Activity View
 */

interface CurrencyActivityView {

    fun showCurrencyList(RCurrencyList: List<Currency>)
}
