package com.microlabs.trallet.view

import com.microlabs.trallet.model.Book

/**
 * Created by Hansen on 3/19/2017.
 */

interface MainActivityView {

    /**
     * Show List of Book
     *
     * @param books = list of Book
     */
    fun showBooks(books: List<Book>)

    /**
     * Show No Books
     *
     * @param books = empty list of Book
     */
    fun showNoBook(books: List<Book>)

    /**
     * Intent to AddNewBookActivity
     */
    fun goToAddNewBook()

    /**
     * Intent to CurrencyActivity
     */
    fun goToCurrency()

    /**
     * Intent to SettingsActivity
     */
    fun goToSettings()
}
