package com.microlabs.trallet.presenter;

import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.AddBookActivityView;

/**
 * Created by Hansen on 5/12/2017.
 * <p>
 * Add Book Activity Presenter
 */

public class AddBookActivityPresenter {

    private AddBookActivityView view;
    private DatabaseBookRepository repo;

    public AddBookActivityPresenter(AddBookActivityView view, DatabaseBookRepository repo) {
        this.view = view;
        this.repo = repo;
    }

    /**
     * Insert New Book To Repo
     *
     * @param title = book title
     * @param desc  = book desc (optional)
     */
    public void insertNewBook(String title, String desc) {
        if (title.isEmpty()) {
            view.showError();
        } else {
            repo.saveBook(title, desc);
            view.done();
        }
    }

    /**
     * Get Book Data (title and desc) to display
     *
     * @param id = bookId of Book to display
     */
    public void getBookData(int id) {
        view.showBook(repo.getBook(id));
    }

    /**
     * Update Book to Repo
     *
     * @param id    = bookId of book to update
     * @param title = book title
     * @param desc  = book desc (optional)
     */
    public void updateBook(int id, String title, String desc) {
        if (id <= 0 || title.isEmpty()) {
            view.showError();
        } else {
            repo.updateBook(id, title, desc);
            view.done();
        }
    }

    public void close() {
        repo.close();
    }
}
