package com.microlabs.trallet.presenter

import com.microlabs.trallet.repo.DatabaseBookRepository
import com.microlabs.trallet.view.MainActivityView

/**
 * Created by Hansen on 3/19/2017.
 *
 * Presenter of Main Activity
 */

class MainActivityPresenter(private val view: MainActivityView, private val bookRepository: DatabaseBookRepository) {

    /**
     * Load books from Repository
     */
    fun loadBooks() {
        val bookList = bookRepository.bookList
        if (bookList.isEmpty()) {
            view.showNoBook(bookList)
        } else {
            view.showBooks(bookList)
        }
    }

    /**
     * Checks Default Currency and Category
     * If not exists, then ask Repository to create them
     */
    fun checkDefault() {
        if (bookRepository.isDefaultCategoryNotExist) {
            bookRepository.loadDefaultCategory()
        }
        if (bookRepository.isDefaultCurrencyNotExist) {
            bookRepository.loadDefaultCurrency()
        }
    }

    /**
     * Intent to AddNewBookActivity
     */
    fun addNewBook() = view.goToAddNewBook()

    /**
     * Intent to CurrencyActivity
     */
    fun toCurrency() = view.goToCurrency()

    /**
     * Intent to SettingsActivity
     */
    fun toSettings() = view.goToSettings()

    /**
     * Delete a Book by its Id
     *
     * @param id = bookId to remove
     */
    fun deleteBook(id: Int) = bookRepository.deleteBook(id)

    /**
     * Close Repository to prevent memory leak
     */
    fun close() = bookRepository.close()
}