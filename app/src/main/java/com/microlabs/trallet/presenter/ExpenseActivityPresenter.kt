package com.microlabs.trallet.presenter

import com.microlabs.trallet.repo.DatabaseBookRepository
import com.microlabs.trallet.view.ExpenseActivityView

/**
 * Created by Hansen on 4/24/2017.
 *
 * Presenter Class of RExpense Activity
 */

class ExpenseActivityPresenter(private val view: ExpenseActivityView, private val bookRepository: DatabaseBookRepository) {

    /**
     * Load Expenses List from its bookId
     * and send it to view
     *
     * @param id = bookId
     */
    fun loadExpense(id: Int) {
//        val expenseList = bookRepository.getExpenseList(id)
//        if (expenseList.isEmpty()) {
//            view.showNoExpense(expenseList)
//        } else {
//            view.showExpenses(expenseList)
//        }
    }

    /**
     * Intent to add new expense
     */
    fun addNewExpense() {
        view.goToAddNewExpense(0)
    }

    /**
     * Intent to edit expense data
     *
     * @param expenseId = expenseId
     */
    fun editExpense(expenseId: Int) {
        view.goToAddNewExpense(expenseId)
    }

    /**
     * Delete expense from book
     *
     * @param bookId = bookId where expense exist
     * Used to decrease book total value
     * @param expenseId = expenseId to be removed
     */
    fun deleteExpense(bookId: Int, expenseId: Int) {
//        bookRepository.deleteExpense(bookId, expenseId)
    }

    /**
     * close Realm repo to prevent memory leak
     */
    fun close() {
//        bookRepository.close()
    }
}
