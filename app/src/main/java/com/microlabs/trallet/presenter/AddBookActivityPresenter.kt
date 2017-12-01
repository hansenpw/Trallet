package com.microlabs.trallet.presenter

import com.microlabs.trallet.repo.DatabaseBookRepository
import com.microlabs.trallet.view.AddBookActivityView

/**
 * Created by Hansen on 5/12/2017.
 *
 *
 * Add Book Activity Presenter
 */

class AddBookActivityPresenter(private val view: AddBookActivityView, private val repo: DatabaseBookRepository) {

    /**
     * Insert New Book To Repo
     *
     * @param title = book title
     * @param desc  = book desc (optional)
     */
    fun insertNewBook(title: String, desc: String) {
        if (title.isEmpty()) {
            view.showError()
        } else {
            repo.saveBook(title, desc)
            view.done()
        }
    }

    /**
     * Get Book Data (title and desc) to display
     *
     * @param id = bookId of Book to display
     */
    fun getBookData(id: Int) {
        view.showBook(repo.getBook(id))
    }

    /**
     * Update Book to Repo
     *
     * @param id    = bookId of book to update
     * @param title = book title
     * @param desc  = book desc (optional)
     */
    fun updateBook(id: Int, title: String, desc: String) {
        if (id <= 0 || title.isEmpty()) {
            view.showError()
        } else {
            repo.updateBook(id, title, desc)
            view.done()
        }
    }

    fun close() = repo.close()
}
