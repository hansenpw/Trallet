package com.microlabs.trallet.presenter;

import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.AddExpenseView;

import java.util.Date;

/**
 * Created by Hansen on 4/28/2017.
 * <p>
 * Add Expense Activity Presenter
 */

public class AddExpenseActivityPresenter {

    private AddExpenseView view;
    private DatabaseBookRepository bookRepository;

    public AddExpenseActivityPresenter(AddExpenseView view, DatabaseBookRepository bookRepository) {
        this.view = view;
        this.bookRepository = bookRepository;
    }

    /**
     * Get Expense Data from Repo
     *
     * @param id = ExpenseId to get
     */
    public void getExpenseData(int id) {
        view.setupData(bookRepository.getExpense(id));
    }

    /**
     * Save New Expense to Repo
     *
     * @param title      = Expense Title
     * @param bookId     = Expense BookId
     * @param value      = Expense Value
     * @param categoryId = Expense CategoryId
     * @param currencyId = Expense CurrencyId
     * @param date       = Expense Date
     * @param details    = Expense Details
     * @return true if success
     */
    public void saveNewExpense(String title, int bookId, Double value, int categoryId, int currencyId, Date date, String details) {
        if (title.isEmpty() || bookId <= 0 || value <= 0.0 ||
                categoryId <= 0 || currencyId <= 0 || date.getTime() <= 0L) {
            view.showError();
        } else {
            bookRepository.saveExpense(title, bookId, value, categoryId, currencyId, date, details);
            view.done();
        }
    }

    /**
     * setup Category Spinner data and adapter
     */
    public void setupCategorySpinner() {
        view.setupCategorySpinner(bookRepository.getCategoryList());
    }

    /**
     * setup Currency Spinner data and adapter
     */
    public void setupCurrencySpinner() {
        view.setupCurrencySpinner(bookRepository.getCurrencyList());
    }

    /**
     * Update Expense Data to Repo
     *
     * @param expenseId  = ExpenseId
     * @param title      = Expense Title
     * @param bookId     = Expense BookId
     * @param value      = Expense Value
     * @param categoryId = Expense CategoryId
     * @param currencyId = Expense CurrencyId
     * @param date       = Expense Date
     * @param details    = Expense Details
     * @param oldValue   = Expense Old Value to update Book Total Value
     * @return true if success
     */
    public void updateExpense(int expenseId, String title, int bookId, double value, int categoryId, int currencyId, Date date, String details, double oldValue) {
        if (expenseId <= 0 || title.isEmpty() || bookId <= 0 || value <= 0.0 ||
                categoryId <= 0 || currencyId <= 0 || date.getTime() <= 0L) {
            view.showError();
        } else {
            bookRepository.updateExpense(expenseId, title, bookId, value, categoryId, currencyId, date, details, oldValue);
            view.done();
        }
    }

    public void close() {
        bookRepository.close();
    }
}
