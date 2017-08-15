package com.microlabs.trallet.view;

import com.microlabs.trallet.model.Book;

import java.util.List;

/**
 * Created by Hansen on 3/19/2017.
 */

public interface MainActivityView {

    /**
     * Show List of Book
     *
     * @param books = list of Book
     */
    void showBooks(List<Book> books);

    /**
     * Show No Books
     *
     * @param books = empty list of Book
     */
    void showNoBook(List<Book> books);

    /**
     * Intent to AddNewBookActivity
     */
    void goToAddNewBook();

    /**
     * Intent to CurrencyActivity
     */
    void goToCurrency();

    /**
     * Intent to SettingsActivity
     */
    void goToSettings();
}
