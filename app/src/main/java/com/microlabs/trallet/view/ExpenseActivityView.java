package com.microlabs.trallet.view;

import com.microlabs.trallet.model.Expense;

import java.util.List;

/**
 * Created by Hansen on 4/24/2017.
 *
 * View of Expense Activity
 */

public interface ExpenseActivityView {

    /**
     * Show no Expenses
     *
     * @param expenses = empty list of expenses for adapter
     */
    void showNoExpense(List<Expense> expenses);

    /**
     * Show Expenses
     *
     * @param expenses = list of expenses for adapter
     */
    void showExpenses(List<Expense> expenses);

    /**
     * Intent to AddExpenseActity for new or edit Expense
     *
     * @param expenseId = expenseId to pass to AddExpenseActivity
     *                  0 for new expense
     *                  >0 for edit expense
     */
    void goToAddNewExpense(int expenseId);
}
