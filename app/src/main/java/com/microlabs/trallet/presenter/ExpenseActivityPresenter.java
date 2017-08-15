package com.microlabs.trallet.presenter;

import com.microlabs.trallet.model.Expense;
import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.ExpenseActivityView;

import java.util.List;

/**
 * Created by Hansen on 4/24/2017.
 *
 * Presenter Class of Expense Activity
 */

public class ExpenseActivityPresenter {

    private ExpenseActivityView view;
    private DatabaseBookRepository bookRepository;

    public ExpenseActivityPresenter(ExpenseActivityView view, DatabaseBookRepository bookRepository) {
        this.view = view;
        this.bookRepository = bookRepository;
    }

    /**
     * Load Expenses List from its bookId
     * and send it to view
     *
     * @param id = bookId
     */
    public void loadExpense(int id) {
        final List<Expense> expenseList = bookRepository.getExpenseList(id);
        if (expenseList.isEmpty()) {
            view.showNoExpense(expenseList);
        } else {
            view.showExpenses(expenseList);
        }
    }

    /**
     * Intent to add new expense
     */
    public void addNewExpense() {
        view.goToAddNewExpense(0);
    }

    /**
     * Intent to edit expense data
     *
     * @param expenseId = expenseId
     */
    public void editExpense(int expenseId) {
        view.goToAddNewExpense(expenseId);
    }

    /**
     * Delete expense from book
     *
     * @param bookId = bookId where expense exist
     *               Used to decrease book total value
     * @param expenseId = expenseId to be removed
     */
    public void deleteExpense(int bookId, int expenseId) {
        bookRepository.deleteExpense(bookId, expenseId);
    }

    /**
     * close Realm repo to prevent memory leak
     */
    public void close() {
        bookRepository.close();
    }
}
