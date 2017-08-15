package com.microlabs.trallet.presenter;

import com.microlabs.trallet.model.Book;
import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.MainActivityView;

import java.util.List;

/**
 * Created by Hansen on 3/19/2017.
 *
 * Presenter of Main Activity
 */

public class MainActivityPresenter {

    private MainActivityView view;
    private DatabaseBookRepository bookRepository;

    public MainActivityPresenter(MainActivityView view, DatabaseBookRepository bookRepository) {
        this.view = view;
        this.bookRepository = bookRepository;
    }

    /**
     * Load books from Repository
     */
    public void loadBooks() {
        List<Book> bookList = bookRepository.getBookList();
        if (bookList.isEmpty()) {
            view.showNoBook(bookList);
        } else {
            view.showBooks(bookList);
        }
    }

    /**
     * Checks Default Currency and Category
     * If not exists, then ask Repository to create them
     */
    public void checkDefault() {
        if (bookRepository.isDefaultCategoryNotExist()) {
            bookRepository.loadDefaultCategory();
        }
        if (bookRepository.isDefaultCurrencyNotExist()) {
            bookRepository.loadDefaultCurrency();
        }
    }

    /**
     * Intent to AddNewBookActivity
     */
    public void addNewBook() {
        view.goToAddNewBook();
    }

    /**
     * Intent to CurrencyActivity
     */
    public void toCurrency() {
        view.goToCurrency();
    }

    /**
     * Intent to SettingsActivity
     */
    public void toSettings() {
        view.goToSettings();
    }

    /**
     * Delete a Book by its Id
     *
     * @param id = bookId to remove
     */
    public void deleteBook(int id) {
        bookRepository.deleteBook(id);
    }

    /**
     * Close Repository to prevent memory leak
     */
    public void close(){
        bookRepository.close();
    }
}