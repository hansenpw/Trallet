package com.microlabs.trallet.view

import com.microlabs.trallet.model.Book

/**
 * Created by Hansen on 5/12/2017.
 *
 * Add Book Activity View
 */

interface AddBookActivityView {
    fun showError()

    fun done()

    fun showBook(book: Book)
}
