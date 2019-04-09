package com.microlabs.trallet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.microlabs.trallet.model.Book
import com.microlabs.trallet.repo.AppDatabase
import com.microlabs.trallet.repo.BookDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddBookViewModel(application: Application) : AndroidViewModel(application) {

    private val bookRepository: BookDao by lazy { AppDatabase.getInstance(getApplication()).bookDao() }

    /**
     * Insert New Book To Repo
     *
     * @param title = book title
     * @param desc  = book desc (optional)
     */
    fun insertNewBook(title: String, desc: String) {
        try {
            GlobalScope.launch(context = Dispatchers.IO) {
                bookRepository.insertBook(Book(title = title, desc = desc))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Update Book to Repo
     *
     * @param id    = bookId of book to update
     * @param title = book title
     * @param desc  = book desc (optional)
     */
    fun updateBook(id: Int, title: String, desc: String) {
        try {
            GlobalScope.launch(Dispatchers.IO) {
                bookRepository.updateBookById(id, title, desc)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}