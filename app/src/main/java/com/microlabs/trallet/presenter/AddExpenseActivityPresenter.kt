package com.microlabs.trallet.presenter

import com.microlabs.trallet.repo.DatabaseBookRepository
import com.microlabs.trallet.view.AddExpenseView
import java.util.*

/**
 * Created by Hansen on 4/28/2017.
 *
 *
 * Add RExpense Activity Presenter
 */

class AddExpenseActivityPresenter(private val view: AddExpenseView, private val bookRepository: DatabaseBookRepository) {

    /**
     * Get RExpense Data from Repo
     *
     * @param id = ExpenseId to get
     */
    fun getExpenseData(id: Int) {
//        view.setupData(bookRepository.getExpense(id))
    }

    /**
     * Save New RExpense to Repo
     *
     * @param title      = RExpense Title
     * @param bookId     = RExpense BookId
     * @param value      = RExpense Value
     * @param categoryId = RExpense CategoryId
     * @param currencyId = RExpense CurrencyId
     * @param date       = RExpense Date
     * @param details    = RExpense Details
     * @return true if success
     */
    fun saveNewExpense(title: String, bookId: Int, value: Double, categoryId: Int, currencyId: Int, date: Date, details: String) {
        if (title.isEmpty() || bookId <= 0 || value <= 0.0 ||
                categoryId <= 0 || currencyId <= 0 || date.time <= 0L) {
            view.showError()
        } else {
//            bookRepository.saveExpense(title, bookId, value, categoryId, currencyId, date, details)
            view.done()
        }
    }

    /**
     * setup RCategory Spinner data and adapter
     */
    fun setupCategorySpinner() {
//        view.setupCategorySpinner(bookRepository.RCategoryList)
    }

    /**
     * setup RCurrency Spinner data and adapter
     */
    fun setupCurrencySpinner() {
//        view.setupCurrencySpinner(bookRepository.RCurrencyList)
    }

    /**
     * Update RExpense Data to Repo
     *
     * @param expenseId  = ExpenseId
     * @param title      = RExpense Title
     * @param bookId     = RExpense BookId
     * @param value      = RExpense Value
     * @param categoryId = RExpense CategoryId
     * @param currencyId = RExpense CurrencyId
     * @param date       = RExpense Date
     * @param details    = RExpense Details
     * @param oldValue   = RExpense Old Value to update Book Total Value
     * @return true if success
     */
    fun updateExpense(expenseId: Int, title: String, bookId: Int, value: Double, categoryId: Int, currencyId: Int, date: Date, details: String, oldValue: Double) {
        if (expenseId <= 0 || title.isEmpty() || bookId <= 0 || value <= 0.0 ||
                categoryId <= 0 || currencyId <= 0 || date.time <= 0L) {
            view.showError()
        } else {
//            bookRepository.updateExpense(expenseId, title, bookId, value, categoryId, currencyId, date, details, oldValue)
            view.done()
        }
    }

//    fun close() = bookRepository.close()
}
