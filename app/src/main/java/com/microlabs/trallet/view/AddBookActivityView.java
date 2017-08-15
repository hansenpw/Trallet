package com.microlabs.trallet.view;

import com.microlabs.trallet.model.Book;

/**
 * Created by Hansen on 5/12/2017.
 *
 * Add Book Activity View
 */

public interface AddBookActivityView {
    void showError();

    void done();

    void showBook(Book book);
}
