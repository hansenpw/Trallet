package com.microlabs.trallet.view

import com.microlabs.trallet.model.Expense

/**
 * Created by Hansen on 4/24/2017.
 *
 * View of RExpense Activity
 */

interface ExpenseActivityView {

    /**
     * Show no Expenses
     *
     * @param expens = empty list of expens for adapter
     */
    fun showNoExpense(expens: List<Expense>)

    /**
     * Show Expenses
     *
     * @param expens = list of expens for adapter
     */
    fun showExpenses(expens: List<Expense>)

    /**
     * Intent to AddExpenseActity for new or edit RExpense
     *
     * @param expenseId = expenseId to pass to AddExpenseActivity
     * 0 for new expense
     * >0 for edit expense
     */
    fun goToAddNewExpense(expenseId: Int)
}
