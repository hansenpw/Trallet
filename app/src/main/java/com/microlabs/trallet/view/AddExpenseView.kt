package com.microlabs.trallet.view

import com.microlabs.trallet.model.Category
import com.microlabs.trallet.model.Currency
import com.microlabs.trallet.model.Expense

/**
 * Created by Hansen on 4/28/2017.
 *
 * Add Expense Activity View
 */

interface AddExpenseView {
    /**
     * Setup Expense Data to fields
     *
     * @param expense = expense object
     */
    fun setupData(expense: Expense)

    /**
     * Show Error when failed to save
     */
    fun showError()

    /**
     * Setup Category Spinner Data and Adapter
     *
     * @param categoryList = List of Category
     */
    fun setupCategorySpinner(categoryList: List<Category>)

    /**
     * Setup Currency Spinner Data and Adapter
     *
     * @param currencyList = List of Currency
     */
    fun setupCurrencySpinner(currencyList: List<Currency>)

    /**
     * Done editing
     * Finish activity
     */
    fun done()
}
