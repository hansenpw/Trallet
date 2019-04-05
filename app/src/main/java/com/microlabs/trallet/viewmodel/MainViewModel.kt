package com.microlabs.trallet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.microlabs.trallet.model.Book
import com.microlabs.trallet.repo.AppDatabase
import com.microlabs.trallet.repo.BookDao

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val bookRepository: BookDao by lazy { AppDatabase.getInstance(getApplication()).bookDao() }

//    init {
//        checkDefault()
//        loadBooks()
//    }

    /**
     * Checks Default RCurrency and RCategory
     * If not exists, then ask Repository to create them
     */
    private fun checkDefault() {
//        if (bookRepository.isDefaultCategoryNotExist) {
//            bookRepository.loadDefaultCategory()
//        }
//        if (bookRepository.isDefaultCurrencyNotExist) {
//            bookRepository.loadDefaultCurrency()
//        }
    }

    /**
     * Load books from Repository
     */
    fun loadBooks() = bookRepository.getAllBooks()

    /**
     * Delete a Book by its Id
     *
     * @param id = bookId to remove
     */
    fun deleteBook(book: Book) = bookRepository.deleteBook(book)

    /**
     * Close Repository to prevent memory leak
     */
//    private fun close() = bookRepository.close()

//    override fun onCleared() {
//        super.onCleared()
//        close()
//    }
}