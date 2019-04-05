package com.microlabs.trallet.view

import com.microlabs.trallet.model.Category
import com.microlabs.trallet.model.Currency
import com.microlabs.trallet.model.Expense

/**
 * Created by Hansen on 4/28/2017.
 *
 * Add RExpense Activity View
 */

interface AddExpenseView {
    /**
     * Setup RExpense Data to fields
     *
     * @param RExpense = RExpense object
     */
    fun setupData(RExpense: Expense)

    /**
     * Show Error when failed to save
     */
    fun showError()

    /**
     * Setup RCategory Spinner Data and Adapter
     *
     * @param RCategoryList = List of RCategory
     */
    fun setupCategorySpinner(RCategoryList: List<Category>)

    /**
     * Setup RCurrency Spinner Data and Adapter
     *
     * @param RCurrencyList = List of RCurrency
     */
    fun setupCurrencySpinner(RCurrencyList: List<Currency>)

    /**
     * Done editing
     * Finish activity
     */
    fun done()
}
