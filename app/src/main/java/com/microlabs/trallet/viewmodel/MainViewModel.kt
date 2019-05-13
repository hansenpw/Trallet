package com.microlabs.trallet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.microlabs.trallet.model.Book
import com.microlabs.trallet.repo.AppDatabase
import com.microlabs.trallet.repo.BookDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val bookRepository: BookDao by lazy { AppDatabase.getInstance(getApplication()).bookDao() }

    /**
     * Load books from Repository
     */
    fun loadBooks() = bookRepository.getAllBooks()

    /**
     * Delete a Book by its Id
     *
     * @param id = bookId to remove
     */
    fun deleteBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            bookRepository.deleteBook(book)
        }
        /*TODO: use invoke on complete using live data of sealed class*/
    }
}