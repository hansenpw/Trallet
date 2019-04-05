package com.microlabs.trallet.view

/**
 * Created by Hansen on 5/11/2017.
 *
 *
 * Add RCurrency Activity View
 */

interface AddCurrencyActivityView {
    fun showError()

    fun done()

    fun showDuplicateError()

    fun validateDeleteCurrency()

    fun showFailDeleteCurrency()
}
