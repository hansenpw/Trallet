package com.microlabs.trallet.view

import com.microlabs.trallet.model.Expense

/**
 * Created by Hansen on 4/24/2017.
 *
 * View of Expense Activity
 */

interface ExpenseActivityView {

    /**
     * Show no Expenses
     *
     * @param expenses = empty list of expenses for adapter
     */
    fun showNoExpense(expenses: List<Expense>)

    /**
     * Show Expenses
     *
     * @param expenses = list of expenses for adapter
     */
    fun showExpenses(expenses: List<Expense>)

    /**
     * Intent to AddExpenseActity for new or edit Expense
     *
     * @param expenseId = expenseId to pass to AddExpenseActivity
     * 0 for new expense
     * >0 for edit expense
     */
    fun goToAddNewExpense(expenseId: Int)
}
