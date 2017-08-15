package com.microlabs.trallet.view;

import com.microlabs.trallet.model.Category;
import com.microlabs.trallet.model.Currency;
import com.microlabs.trallet.model.Expense;

import java.util.List;

/**
 * Created by Hansen on 4/28/2017.
 *
 * Add Expense Activity View
 */

public interface AddExpenseView {
    /**
     * Setup Expense Data to fields
     *
     * @param expense = expense object
     */
    void setupData(Expense expense);

    /**
     * Show Error when failed to save
     */
    void showError();

    /**
     * Setup Category Spinner Data and Adapter
     *
     * @param categoryList = List of Category
     */
    void setupCategorySpinner(List<Category> categoryList);

    /**
     * Setup Currency Spinner Data and Adapter
     *
     * @param currencyList = List of Currency
     */
    void setupCurrencySpinner(List<Currency> currencyList);

    /**
     * Done editing
     * Finish activity
     */
    void done();
}
